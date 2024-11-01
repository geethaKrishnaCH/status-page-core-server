package com.easylearnz.status_page.core.user.controller;

import com.easylearnz.status_page.core.user.dto.UserInfoResponse;
import com.easylearnz.status_page.core.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("/{organizationId}")
    public ResponseEntity<?> getUserInfo(
            @RequestAttribute String userId,
            @PathVariable(value = "organizationId") String organizationId
    ) {
        UserInfoResponse response = userInfoService.getUserInfo(userId, organizationId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
