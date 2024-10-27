package com.easylearnz.status_page.organization.dto;

import lombok.Data;

@Data
public class CreateOrganizationRequest {
    private String name;
    private String displayName;
}
