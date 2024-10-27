package com.easylearnz.status_page.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
    private String title;
    private String description;
    private String status = "Open";
    private String severity;
    private String incidentType;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
