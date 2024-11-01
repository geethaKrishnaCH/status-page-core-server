package com.easylearnz.status_page.core.incident.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddIncidentRequest {
    private String incidentName;
    private String incidentStatus;
    private String message;
    private List<Integer> services;
    private String serviceStatus;
}
