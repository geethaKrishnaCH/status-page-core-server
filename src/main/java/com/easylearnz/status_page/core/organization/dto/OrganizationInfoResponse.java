package com.easylearnz.status_page.core.organization.dto;

import com.easylearnz.status_page.models.OrgService;
import com.easylearnz.status_page.models.enums.ServiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationInfoResponse {
    private String organizationId;
    private String name;
    private String displayName;
    private String status;
}
