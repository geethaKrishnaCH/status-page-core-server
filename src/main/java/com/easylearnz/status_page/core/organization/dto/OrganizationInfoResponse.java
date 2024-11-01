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

    public void setOverallStatus(List<OrgService> services) {
        if (services == null || services.isEmpty()) {
            status = "N/A"; // No services to determine the status
            return;
        }

        Map<ServiceStatus, Integer> statusCount = new HashMap<>();

        // Count the number of services by their status
        for (OrgService service : services) {
            statusCount.put(service.getStatus(), statusCount.getOrDefault(service.getStatus(), 0) + 1);
        }

        // Determine the overall status based on the counts
        if (statusCount.containsKey(ServiceStatus.MAJOR_OUTAGE)) {
            status = ServiceStatus.MAJOR_OUTAGE.name();
        } else if (statusCount.containsKey(ServiceStatus.PARTIAL_OUTAGE)) {
            status = ServiceStatus.PARTIAL_OUTAGE.name();
        } else if (statusCount.containsKey(ServiceStatus.DEGRADED)) {
            status = ServiceStatus.DEGRADED.name();
        } else if (statusCount.containsKey(ServiceStatus.MAINTENANCE)) {
            status = ServiceStatus.MAINTENANCE.name();
        } else {
            status = ServiceStatus.OPERATIONAL.name(); // All services are operational
        }
    }
}
