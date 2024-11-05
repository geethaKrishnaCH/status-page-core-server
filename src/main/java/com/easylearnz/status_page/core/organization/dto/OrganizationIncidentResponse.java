package com.easylearnz.status_page.core.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationIncidentResponse {
    private Integer id;
    private String name;
    private String description;
    private String status;
    private String serviceStatus;
    private String lastUpdated;
    private String lastMessage;
    private List<IncidentServicesResponse> services;

    @Data
    @Builder
    public static class IncidentServicesResponse {
        private Integer id;
        private String name;
    }
}
