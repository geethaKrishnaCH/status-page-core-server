package com.easylearnz.status_page.core.user.service;

import com.easylearnz.status_page.auth0.dto.Auth0UserInfoResponse;
import com.easylearnz.status_page.auth0.service.Auth0UserManagementService;
import com.easylearnz.status_page.config.UserService;
import com.easylearnz.status_page.core.user.dto.UserInfoResponse;
import com.easylearnz.status_page.models.Invitation;
import com.easylearnz.status_page.models.Organization;
import com.easylearnz.status_page.models.User;
import com.easylearnz.status_page.models.UserRole;
import com.easylearnz.status_page.repo.InvitationRepo;
import com.easylearnz.status_page.repo.OrganizationRepo;
import com.easylearnz.status_page.repo.UserRepo;
import com.easylearnz.status_page.repo.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepo userRepo;
    private final UserService userService;
    private final UserRoleRepo userRoleRepo;
    private final InvitationRepo invitationRepo;
    private final OrganizationRepo organizationRepo;
    private final Auth0UserManagementService auth0UserManagementService;

    public UserInfoResponse getUserInfo(String userId, String organizationId, String currentOrganizationId) {

        User userInfo = userRepo.findByUserId(userId);
        if (userInfo == null) {
            User newUser = createUserWithoutOrganization(userId);
            if (organizationId == null) {
                return UserInfoResponse.builder().name(newUser.getName()).build();
            } else {
                // mark the invitation true
                Organization organization = organizationRepo.findByOrganizationId(organizationId);
                Invitation invitation = invitationRepo.findByOrganizationAndInviteeAndAccepted(organization, newUser.getEmail(), false);
                if (invitation != null) {
                    invitation.setAccepted(true);
                    invitationRepo.save(invitation);

                    // create newUser role
                    UserRole userRole = new UserRole();
                    userRole.setRole(invitation.getRole());
                    userRole.setUser(newUser);
                    userRoleRepo.save(userRole);
                    return UserInfoResponse.builder()
                            .organizationId(organizationId)
                            .roles(Arrays.asList(userRole.getRole().getName()))
                            .name(newUser.getName())
                            .userId(newUser.getUserId())
                            .build();
                } else {
                    // get user from auth0
                }
            }
        }
        if (!checkIfUserBelongsToOrganization(userId, currentOrganizationId) || !currentOrganizationId.equals(organizationId)) {
            return UserInfoResponse.builder().name(userInfo.getName()).build();
        }

        List<String> roles = userService.extractUserRoles(userId, organizationId);

        return UserInfoResponse.builder().name(userInfo.getName()).userId(userId).organizationId(organizationId).roles(roles).build();
    }

    public UserInfoResponse getUserInfoFromToken(String userId, String organizationId) {

        User userInfo = userRepo.findByUserId(userId);
        if (userInfo == null) {

            User newUser = createUserWithoutOrganization(userId);

            if (organizationId != null) {
                // mark the invitation true
                Organization organization = organizationRepo.findByOrganizationId(organizationId);
                Invitation invitation = invitationRepo.findByOrganizationAndInviteeAndAccepted(organization, newUser.getEmail(), false);
                if (invitation != null) {
                    invitation.setAccepted(true);
                    invitationRepo.save(invitation);

                    // create newUser role
                    UserRole userRole = new UserRole();
                    userRole.setRole(invitation.getRole());
                    userRole.setUser(newUser);
                    userRoleRepo.save(userRole);
                }
            }
            return UserInfoResponse.builder().name(newUser.getName()).build();
        }

        return UserInfoResponse.builder().name(userInfo.getName()).build();
    }

    private boolean checkIfUserBelongsToOrganization(String userId, String organizationId) {
        List<UserRole> userRoles = userRoleRepo.findByUserId(userId);
        Long collect = userRoles.stream().filter(ur -> ur.getRole().getOrganization().getOrganizationId().equals(organizationId)).collect(Collectors.counting());
        return collect > 0;
    }

    private User createUserWithoutOrganization(String userId) {
        Auth0UserInfoResponse auth0UserInfo = auth0UserManagementService.getUserInfo(userId);
        // create the user
        User user = new User();
        user.setName(auth0UserInfo.getNickname());
        user.setEmail(auth0UserInfo.getEmail());
        user.setActive(true);
        user.setUserId(auth0UserInfo.getUserId());
        return userRepo.save(user);
    }
}
