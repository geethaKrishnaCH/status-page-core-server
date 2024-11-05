package com.easylearnz.status_page.auth0.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Auth0AssignUserRoleToOrganizationRequest {
    private List<String> roles;
}
