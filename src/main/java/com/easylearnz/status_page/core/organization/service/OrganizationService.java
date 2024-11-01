package com.easylearnz.status_page.core.organization.service;

import com.easylearnz.status_page.auth0.dto.Auth0CreateOrganizationRequest;
import com.easylearnz.status_page.auth0.dto.Auth0CreateOrganizationResponse;
import com.easylearnz.status_page.auth0.dto.Auth0UserInfoResponse;
import com.easylearnz.status_page.auth0.service.Auth0OrgManagementService;
import com.easylearnz.status_page.auth0.service.Auth0UserManagementService;
import com.easylearnz.status_page.core.organization.dto.*;
import com.easylearnz.status_page.models.*;
import com.easylearnz.status_page.models.enums.DefaultRole;
import com.easylearnz.status_page.repo.*;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final OrgServiceRepo orgServiceRepo;
    private final UserRoleRepo userRoleRepo;
    private final OrganizationRepo organizationRepo;
    private final Auth0OrgManagementService auth0OrgManagementService;
    private final Auth0UserManagementService auth0UserManagementService;

    @Value("${auth0.default.db.connection}")
    private String defaultConnectionId;
    @Value("${auth0.role.admin}")
    private String adminRoleId;

    public CreateOrganizationResponse createOrganization(CreateOrganizationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        // create organization in auth0
        Auth0CreateOrganizationRequest auth0Request = Auth0CreateOrganizationRequest.builder()
                .name(request.getName())
                .displayName(request.getDisplayName())
                .enabledConnections(Arrays.asList(getDefaultConnection()))
                .build();
        Auth0CreateOrganizationResponse auth0Org = auth0OrgManagementService.createOrganization(auth0Request);

        // add the current user to the organization as admin
        String newOrgId = auth0Org.getId();
        auth0UserManagementService.addUserToOrganization(newOrgId, userId);
        auth0UserManagementService.assignUserRoleToOrganization(newOrgId, userId, adminRoleId);

        // save organization in db
        Organization newOrganization = new Organization();
        newOrganization.setOrganizationId(newOrgId);
        newOrganization.setName(auth0Org.getName());
        newOrganization.setDisplayName(auth0Org.getDisplayName());
        newOrganization.setCreatedAt(LocalDateTime.now());
        newOrganization.setUpdatedAt(LocalDateTime.now());

        newOrganization = organizationRepo.save(newOrganization);

        // add default roles
        List<Role> defaultRoles = addDefaultRoles(newOrganization);

        // save the user if not already there
        User existingUser = userRepo.findByUserId(userId);
        if (existingUser == null) {
            Auth0UserInfoResponse userInfo = auth0UserManagementService.getUserInfo(userId);
            User newUser = new User();
            newUser.setUserId(userId);
            newUser.setActive(true);
            newUser.setEmail(userInfo.getEmail());
            newUser.setName(userInfo.getNickname());
            userRepo.save(newUser);
            createUserRole(newUser, defaultRoles);
        } else {
            createUserRole(existingUser, defaultRoles);
        }

        return CreateOrganizationResponse.builder()
                .organizationId(newOrgId)
                .name(auth0Org.getName())
                .displayName(auth0Org.getDisplayName())
                .build();
    }

    private void createUserRole(User user, List<Role> defaultRoles) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(defaultRoles.stream()
                .filter(role -> role.getName().equals(DefaultRole.ADMINISTRATOR.getName()))
                .findFirst()
                .get()
        );
        userRoleRepo.save(userRole);
    }

    private List<Role> addDefaultRoles(Organization organization) {
        List<Role> defaultRoles = new ArrayList<>();
        for (DefaultRole role : DefaultRole.values()) {
            Role newRole = new Role();
            newRole.setOrganization(organization);
            newRole.setName(role.getName());
            newRole = roleRepo.save(newRole);
            defaultRoles.add(newRole);
        }

        return defaultRoles;
    }

    private Auth0CreateOrganizationRequest.EnabledConnection getDefaultConnection() {
        return Auth0CreateOrganizationRequest.EnabledConnection
                .builder()
                .connectionId(defaultConnectionId)
                .assignMembershipOnLogin(true)
                .showAsButton(true)
                .singUpEnabled(false)
                .build();
    }

    public List<OrganizationResponse> fetchOrganizations(String query) {
        if (StringUtils.isBlank(query)) {
            query = "";
        }
        List<OrganizationResponse> organizations = organizationRepo.findByName("%" + query + "%");
        return organizations;
    }

    public OrganizationInfoResponse fetchOrganizationInfo(String organizationId) {
        Organization organization = organizationRepo.findByOrganizationId(organizationId);
        List<OrgService> services = orgServiceRepo.findByOrganization(organization);
        OrganizationInfoResponse res = OrganizationInfoResponse
                .builder()
                .organizationId(organization.getOrganizationId())
                .displayName(organization.getDisplayName())
                .name(organization.getName())
                .build();
        res.setOverallStatus(services);
        return res;
    }

    public List<OrganizationServiceResponse> getOrganizationServices(String organizationId) {
        Organization organization = organizationRepo.findByOrganizationId(organizationId);
        List<OrgService> services = orgServiceRepo.findByOrganization(organization);
        return services.stream()
                .map(s -> OrganizationServiceResponse.builder()
                        .serviceId(s.getId())
                        .name(s.getName())
                        .description(s.getDescription())
                        .status(s.getStatus().name())
                        .url(s.getUrl())
                        .category(s.getCategory())
                        .build())
                .collect(Collectors.toList());
    }
}
