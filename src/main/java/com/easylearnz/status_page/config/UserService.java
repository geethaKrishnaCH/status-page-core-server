package com.easylearnz.status_page.config;

import com.easylearnz.status_page.auth0.service.Auth0UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Auth0UserManagementService auth0UserManagementService;

    public String getCurrentUserSubject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getSubject();
        }
        throw new RuntimeException("No authenticated user found");
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String extractOrganizationId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Map<String, Object> claims = jwt.getClaims();
            return (String) claims.get("org_id");
        }
        throw new RuntimeException("No authenticated user found");
    }

    public List<String> extractUserRoles(String userId, String organizationId) {
        List<String> roles = auth0UserManagementService.getRolesForUserInOrganization(organizationId, userId);
        return roles;
    }

    public void updateSecurityContext(String userId, String organizationId) {
        List<String> roles = extractUserRoles(userId, organizationId);
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                .collect(Collectors.toList());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            authentication = new JwtAuthenticationToken(
                    jwt,
                    authorities,
                    jwt.getSubject()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}