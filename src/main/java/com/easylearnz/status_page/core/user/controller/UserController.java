package com.easylearnz.status_page.core.user.controller;

import com.easylearnz.status_page.core.user.dto.OrgUserInfoResponse;
import com.easylearnz.status_page.core.user.dto.UserInfoResponse;
import com.easylearnz.status_page.core.user.dto.UserInviteRequest;
import com.easylearnz.status_page.core.user.service.UserInfoService;
import com.easylearnz.status_page.core.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;
    private final UserManagementService userManagementService;

    @GetMapping("/info/{organizationId}")
    public ResponseEntity<?> getUserInfo(
            @RequestAttribute String userId,
            @RequestAttribute(value = "organizationId", required = false) String organizationId,
            @PathVariable("organizationId") String currentOrganizationId

    ) {

        UserInfoResponse response = userInfoService.getUserInfo(userId, organizationId, currentOrganizationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfoFromToken(
            @RequestAttribute String userId,
            @RequestAttribute(value = "tokenOrganizationId", required = false) String organizationId

    ) {
        UserInfoResponse response = userInfoService.getUserInfoFromToken(userId, organizationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> getUsersInOrganization(@RequestAttribute("organizationId") String organizationId) {
        List<OrgUserInfoResponse> users = userManagementService.getAllUsersInTheOrganization(organizationId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/invitations")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> createUserInvite(
            @RequestBody UserInviteRequest req,
            @RequestAttribute("organizationId") String organizationId,
            @RequestAttribute("userId") String userId
    ) {
        userManagementService.createUserInvitation(req.getEmail(), organizationId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
