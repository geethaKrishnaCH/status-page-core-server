package com.easylearnz.status_page.auth0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Auth0CreateOrganizationResponse {
    private String id;
    @JsonProperty("display_name")
    private String displayName;
    private String name;
}
