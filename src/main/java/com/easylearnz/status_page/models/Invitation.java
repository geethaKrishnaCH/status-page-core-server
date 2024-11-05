package com.easylearnz.status_page.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "invite_id", nullable = false, unique = true)
    private String inviteId;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    private String inviter;

    private String invitee;

    @Column(name = "invitation_url")
    private String invitationUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "connection_id")
    private String connectionId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "ticket_id")
    private String ticketId;

    private boolean accepted;
}
