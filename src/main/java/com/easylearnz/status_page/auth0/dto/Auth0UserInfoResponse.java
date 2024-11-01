package com.easylearnz.status_page.auth0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Auth0UserInfoResponse {
    @JsonProperty("user_id")
    private String userId;
    private String email;
    private String name;
    private String picture;
    private String nickname;
}
