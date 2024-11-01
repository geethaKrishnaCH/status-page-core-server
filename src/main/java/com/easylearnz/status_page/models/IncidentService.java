package com.easylearnz.status_page.models;

import com.easylearnz.status_page.core.incident.dto.AddIncidentRequest;
import com.easylearnz.status_page.models.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "incident_services")
public class IncidentService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private OrgService service;

    @Column(name = "service_status")
    private ServiceStatus serviceStatus;
}
