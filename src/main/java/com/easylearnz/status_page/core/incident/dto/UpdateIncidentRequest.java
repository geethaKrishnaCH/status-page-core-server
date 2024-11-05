package com.easylearnz.status_page.core.incident.dto;

import lombok.Data;

@Data
public class UpdateIncidentRequest {
    private Integer id;
    private String title;
    private String description;
    private String status;
    private String message;
}
