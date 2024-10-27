package com.easylearnz.status_page.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "organization_id", unique = true, nullable = false)
    private String organizationId;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "organization")
    private Set<User> users;
    @OneToMany(mappedBy = "organization")
    private Set<Service> services;
}
