package com.easylearnz.status_page.auth0.service;

import com.easylearnz.status_page.auth0.dto.Auth0CreateOrganizationRequest;
import com.easylearnz.status_page.auth0.dto.Auth0CreateOrganizationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class Auth0OrgManagementService {
    private final RestTemplate restTemplate;
    private final Auth0TokenService auth0TokenService;

    @Value("${auth0.management.api.domain}")
    private String domain;

    public Auth0CreateOrganizationResponse createOrganization(Auth0CreateOrganizationRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(auth0TokenService.getAuth0ManagementToken());
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Auth0CreateOrganizationRequest> entity = new HttpEntity<>(req, headers);
        ResponseEntity<Auth0CreateOrganizationResponse> response = restTemplate.postForEntity(
                domain + "organizations", entity, Auth0CreateOrganizationResponse.class);

        return response.getBody();
    }

}
