package com.easylearnz.status_page.core.incident.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddIncidentRequest {
    private String title;
    private String description;
    private String status;
    private String message;
    private List<Integer> selectedServices;
    private String serviceStatus;
}
