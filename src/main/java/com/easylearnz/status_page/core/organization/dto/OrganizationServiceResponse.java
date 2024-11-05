package com.easylearnz.status_page.core.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationServiceResponse {
    private Integer serviceId;
    private String name;
    private String description;
    private String url;
    private String category;
    private String status;
}
