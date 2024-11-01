package com.easylearnz.status_page.core.user.service;

import com.easylearnz.status_page.config.UserService;
import com.easylearnz.status_page.core.user.dto.UserInfoResponse;
import com.easylearnz.status_page.models.User;
import com.easylearnz.status_page.models.UserRole;
import com.easylearnz.status_page.repo.UserRepo;
import com.easylearnz.status_page.repo.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepo userRepo;
    private final UserService userService;
    private final UserRoleRepo userRoleRepo;

    public UserInfoResponse getUserInfo(String userId, String organizationId) {

        if (!checkIfUserBelongsToOrganization(userId, organizationId)) {
            return UserInfoResponse.builder().build();
        }

        List<String> roles = userService.extractUserRoles(userId, organizationId);

        User userInfo = userRepo.findByUserId(userId);
        return UserInfoResponse.builder()
                .name(userInfo.getName())
                .userId(userId)
                .organizationId(organizationId)
                .roles(roles)
                .build();
    }

    private boolean checkIfUserBelongsToOrganization(String userId, String organizationId) {
        List<UserRole> userRoles = userRoleRepo.findByUserId(userId);
        Long collect = userRoles.stream()
                .filter(ur -> ur.getRole().getOrganization().getOrganizationId().equals(organizationId))
                .collect(Collectors.counting());
        return collect > 0;
    }
}
