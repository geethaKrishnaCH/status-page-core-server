package com.easylearnz.status_page.organization.service;

import com.easylearnz.status_page.auth0.dto.Auth0CreateOrganizationRequest;
import com.easylearnz.status_page.auth0.dto.Auth0CreateOrganizationResponse;
import com.easylearnz.status_page.auth0.service.Auth0OrgManagementService;
import com.easylearnz.status_page.auth0.service.Auth0UserManagementService;
import com.easylearnz.status_page.models.Organization;
import com.easylearnz.status_page.organization.dto.CreateOrganizationRequest;
import com.easylearnz.status_page.organization.dto.CreateOrganizationResponse;
import com.easylearnz.status_page.organization.dto.OrganizationResponse;
import com.easylearnz.status_page.repo.OrganizationRepo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

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
        Auth0CreateOrganizationRequest auth0Request = Auth0CreateOrganizationRequest.builder()
                .name(request.getName())
                .displayName(request.getDisplayName())
                .enabledConnections(Arrays.asList(getDefaultConnection()))
                .build();
        Auth0CreateOrganizationResponse createOrgRes = auth0OrgManagementService.createOrganization(auth0Request);

        String orgId = createOrgRes.getId();
        auth0UserManagementService.addUserToOrganization(orgId, userId);
        auth0UserManagementService.assignUserRoleToOrganization(orgId, userId, adminRoleId);

        Organization newOrganization = new Organization();
        newOrganization.setOrganizationId(orgId);
        newOrganization.setName(createOrgRes.getName());
        newOrganization.setDisplayName(createOrgRes.getDisplayName());
        newOrganization.setCreatedAt(LocalDateTime.now());
        newOrganization.setUpdatedAt(LocalDateTime.now());

        organizationRepo.save(newOrganization);

        return CreateOrganizationResponse.builder()
                .organizationId(orgId)
                .name(createOrgRes.getName())
                .displayName(createOrgRes.getDisplayName())
                .build();
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
}
