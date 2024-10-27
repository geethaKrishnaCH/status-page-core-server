package com.easylearnz.status_page.auth0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Auth0CreateOrganizationRequest {
    private String name;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("enabled_connections")
    private List<EnabledConnection> enabledConnections;

    @Data
    @Builder
    public static class EnabledConnection {
        @JsonProperty("connection_id")
        private String connectionId;
        @JsonProperty("assign_membership_on_login")
        private boolean assignMembershipOnLogin;
        @JsonProperty("show_as_button")
        private boolean showAsButton;
        @JsonProperty("is_signup_enabled")
        private boolean singUpEnabled;
    }
}
