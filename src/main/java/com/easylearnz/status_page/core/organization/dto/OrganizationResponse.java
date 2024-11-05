package com.easylearnz.status_page.core.organization.dto;

import lombok.Data;

@Data
public class OrganizationResponse {
    private String organizationId;
    private String name;
    private String displayName;

    public OrganizationResponse(String organizationId, String name, String displayName) {
        this.organizationId = organizationId;
        this.name = name;
        this.displayName = displayName;
    }
}
