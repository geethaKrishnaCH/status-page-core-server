package com.easylearnz.status_page.organization.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrganizationResponse {
    private String organizationId;
    private String name;
    private String displayName;
}