package com.easylearnz.status_page.auth0.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class Auth0InvitationRequest {
    private Inviter inviter;
    private Invitee invitee;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("connection_id")
    private String connectionId;
    private List<String> roles;

    public void setInviter(String name) {
        this.inviter = Inviter.builder().name(name).build();
    }

    public void setInvitee(String email) {
        this.invitee = Invitee.builder().email(email).build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Inviter {
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Invitee {
        private String email;
    }
}
