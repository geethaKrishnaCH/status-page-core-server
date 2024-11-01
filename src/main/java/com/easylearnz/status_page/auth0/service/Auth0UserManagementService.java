package com.easylearnz.status_page.auth0.service;

import com.easylearnz.status_page.auth0.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Auth0UserManagementService {
    private final RestTemplate restTemplate;
    private final Auth0TokenService auth0TokenService;

    @Value("${auth0.management.api.domain}")
    private String domain;

    public boolean addUserToOrganization(String organizationId, String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(auth0TokenService.getAuth0ManagementToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Auth0AddUserToOrganizationRequest req = Auth0AddUserToOrganizationRequest
                .builder()
                .members(Arrays.asList(userId))
                .build();

        HttpEntity<Auth0AddUserToOrganizationRequest> entity = new HttpEntity<>(req, headers);
        restTemplate.postForEntity(
                domain + "organizations/{orgId}/members".replace("{orgId}", organizationId), entity, Auth0CreateOrganizationResponse.class);

        return true;
    }

    public boolean assignUserRoleToOrganization(String organizationId, String userId, String roleId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(auth0TokenService.getAuth0ManagementToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Auth0AssignUserRoleToOrganizationRequest req = Auth0AssignUserRoleToOrganizationRequest
                .builder()
                .roles(Arrays.asList(roleId))
                .build();

        HttpEntity<Auth0AssignUserRoleToOrganizationRequest> entity = new HttpEntity<>(req, headers);
        String urlTemplate = "organizations/{orgId}/members/{userId}/roles";
        String url = urlTemplate.replace("{orgId}", organizationId)
                .replace("{userId}", userId);
        restTemplate.postForEntity(
                domain + url, entity, Auth0CreateOrganizationResponse.class);

        return true;
    }

    public List<String> getRolesForUserInOrganization(String organizationId, String userId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(auth0TokenService.getAuth0ManagementToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            String urlTemplate = "organizations/{orgId}/members/{userId}/roles";
            String url = urlTemplate.replace("{orgId}", organizationId)
                    .replace("{userId}", userId);
            ResponseEntity<Auth0RoleResponse[]> response = restTemplate.exchange(
                    domain + url,
                    HttpMethod.GET,
                    entity,
                    Auth0RoleResponse[].class
            );

            return Arrays.stream(response.getBody())
                    .map(Auth0RoleResponse::getName)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Auth0UserInfoResponse getUserInfo(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(auth0TokenService.getAuth0ManagementToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity<>(headers);
        String urlTemplate = "users/{userId}";
        String url = urlTemplate.replace("{userId}", userId);
        ResponseEntity<Auth0UserInfoResponse> response = restTemplate.exchange(
                domain + url,
                HttpMethod.GET,
                entity,
                Auth0UserInfoResponse.class
        );

        return response.getBody();

    }
}
