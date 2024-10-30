package com.easylearnz.status_page.config;

import com.easylearnz.status_page.auth0.service.Auth0UserManagementService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Auth0Filter extends OncePerRequestFilter {

    private final Auth0UserManagementService auth0UserManagementService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = userService.extractToken(request);
            if (token != null) {
                String organizationId = userService.extractOrganizationId();
                String userId = userService.getCurrentUserSubject();

                if (organizationId != null) {
                    List<String> roles = auth0UserManagementService.getRolesForUserInOrganization(organizationId, userId);
                    userService.updateSecurityContext(roles);
                }
            }
        } catch (Exception e) {
            log.error("Error processing Auth0 roles", e);
        }

        filterChain.doFilter(request, response);
    }


}
