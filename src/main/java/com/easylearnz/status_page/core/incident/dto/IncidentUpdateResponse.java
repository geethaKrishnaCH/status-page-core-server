package com.easylearnz.status_page.core.incident.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IncidentUpdateResponse {
    private String status;
    private String message;
    private String timestamp;
}