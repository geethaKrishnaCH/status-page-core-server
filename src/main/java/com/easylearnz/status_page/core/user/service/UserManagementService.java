package com.easylearnz.status_page.core.user.service;

import com.easylearnz.status_page.auth0.dto.Auth0InvitationResponse;
import com.easylearnz.status_page.auth0.service.Auth0UserManagementService;
import com.easylearnz.status_page.core.user.dto.OrgUserInfoResponse;
import com.easylearnz.status_page.models.*;
import com.easylearnz.status_page.models.enums.DefaultRole;
import com.easylearnz.status_page.repo.*;
import com.easylearnz.status_page.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final UserRoleRepo userRoleRepo;
    private final InvitationRepo invitationRepo;
    private final OrganizationRepo organizationRepo;
    private final Auth0UserManagementService auth0UserManagementService;

    public List<OrgUserInfoResponse> getAllUsersInTheOrganization(String organizationId) {
        Organization organization = organizationRepo.findByOrganizationId(organizationId);
        List<Role> roles = roleRepo.findByOrganization(organization);
        List<UserRole> allUserRoles = userRoleRepo.findByRoleIn(roles);
        return allUserRoles.stream().collect(Collectors.groupingBy(UserRole::getUser))
                .entrySet().stream()
                .map(entry -> {
                    User user = entry.getKey();
                    List<String> userRoles = entry.getValue().stream()
                            .map(userRole -> userRole.getRole().getName())
                            .collect(Collectors.toList());
                    return OrgUserInfoResponse.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .userId(user.getUserId())
                            .name(user.getName())
                            .status(user.isActive() ? "ACTIVE" : "IN-ACTIVE")
                            .roles(userRoles)
                            .build();
                })
                .collect(Collectors.toList());

    }

    public void createUserInvitation(String email, String organizationId, String inviterId) {
        User user = userRepo.findByUserId(inviterId);
        Organization organization = organizationRepo.findByOrganizationId(organizationId);
        Role viewerRole = roleRepo.findByOrganization(organization).stream()
                .filter(role -> role.getName().equals(DefaultRole.VIEWER.getName()))
                .findFirst().orElseThrow();
        Auth0InvitationResponse invitationRes = auth0UserManagementService
                .createUserInvitationToOrganization(organizationId, email, user.getName(), viewerRole.getRoleId());

        System.out.println(invitationRes);

        Invitation invitation = getInvitation(invitationRes, organization, viewerRole);

        invitationRepo.save(invitation);
    }

    private static Invitation getInvitation(Auth0InvitationResponse invitationRes, Organization organization, Role role) {
        Invitation invitation = new Invitation();
        invitation.setInviteId(invitationRes.getInviteId());
        invitation.setOrganization(organization);
        invitation.setInviter(invitationRes.getInviter().getName());
        invitation.setInvitee(invitationRes.getInvitee().getEmail());
        invitation.setInvitationUrl(invitationRes.getInvitationUrl());
        invitation.setCreatedAt(DateUtil.convertUTCTimeStringToDate(invitationRes.getCreatedAt()));
        invitation.setExpiresAt(DateUtil.convertUTCTimeStringToDate(invitationRes.getExpiresAt()));
        invitation.setClientId(invitationRes.getClientId());
        invitation.setConnectionId(invitationRes.getConnectionId());
        invitation.setRole(role);
        invitation.setTicketId(invitationRes.getTicketId());
        invitation.setAccepted(false);
        return invitation;
    }
}
