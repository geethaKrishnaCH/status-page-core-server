package com.easylearnz.status_page.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Auth0Filter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = userService.extractToken(request);
            if (token != null) {
                String organizationId = userService.extractOrganizationId();
                String userId = userService.getCurrentUserSubject();
                request.setAttribute("userId", userId);

                if (organizationId != null) {
                    request.setAttribute("organizationId", organizationId);
                    userService.updateSecurityContext(userId, organizationId);
                }
            }
        } catch (Exception e) {
            log.error("Error processing Auth0 roles", e);
        }

        filterChain.doFilter(request, response);
    }


}
