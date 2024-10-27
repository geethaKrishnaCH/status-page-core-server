package com.easylearnz.status_page.auth0.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Auth0TokenService {

    @Value("${auth0.management.token.clientId}")
    private String clientId;
    @Value("${auth0.management.token.clientSecret}")
    private String clientSecret;
    @Value("${auth0.management.token.audience}")
    private String audience;
    @Value("${auth0.management.token.domain}")
    private String domain;

    private final RestTemplate restTemplate;

    public String getAuth0ManagementToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", audience);
        body.put("grant_type", "client_credentials");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                domain + "/oauth/token", entity, Map.class);

        return response.getBody().get("access_token").toString();
    }

}
