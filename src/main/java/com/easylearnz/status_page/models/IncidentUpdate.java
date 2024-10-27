package com.easylearnz.status_page.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "incident_updates")
public class IncidentUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "incident_id", nullable = false)
    private Incident incident;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String message;
    private String status;
    private String updateType;
    private Boolean isPublic = true;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
