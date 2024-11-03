package com.easylearnz.status_page.auth0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class Auth0InvitationResponse {
    @JsonProperty("id")
    private String inviteId;
    @JsonProperty("organization_id")
    private String organizationId;
    private Inviter inviter;
    private Invitee invitee;
    @JsonProperty("invitation_url")
    private String invitationUrl;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("expires_at")
    private String expiresAt;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("connection_id")
    private String connectionId;
    private List<String> roles;
    @JsonProperty("ticket_id")
    private String ticketId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Inviter {
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Invitee {
        private String email;
    }
}
