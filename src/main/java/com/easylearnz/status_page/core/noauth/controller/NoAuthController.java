package com.easylearnz.status_page.core.noauth.controller;

import com.easylearnz.status_page.core.incident.dto.AddIncidentRequest;
import com.easylearnz.status_page.core.incident.dto.IncidentUpdateResponse;
import com.easylearnz.status_page.core.incident.dto.UpdateIncidentRequest;
import com.easylearnz.status_page.core.incident.service.IncidentManagementService;
import com.easylearnz.status_page.core.organization.dto.OrganizationIncidentResponse;
import com.easylearnz.status_page.core.organization.dto.OrganizationInfoResponse;
import com.easylearnz.status_page.core.organization.dto.OrganizationResponse;
import com.easylearnz.status_page.core.organization.dto.OrganizationServiceResponse;
import com.easylearnz.status_page.core.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class NoAuthController {
    private final OrganizationService organizationService;
    private final IncidentManagementService incidentManagementService;

    @GetMapping("/organizations/{organizationId}/services")
    public ResponseEntity<?> getOrganizationServicesV2(@PathVariable String organizationId) {
        List<OrganizationServiceResponse> res = organizationService.getOrganizationServices(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/organizations/{organizationId}/incidents")
    public ResponseEntity<?> getOrganizationIncidentsV2(@PathVariable String organizationId) {
        List<OrganizationIncidentResponse> res = organizationService.getOrganizationIncidents(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/organizations")
    public ResponseEntity<?> fetchOrganizations(@RequestParam(value = "query", required = false) String query) {
        List<OrganizationResponse> res = organizationService.fetchOrganizations(query);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<?> fetchOrganizationInfo(@PathVariable("organizationId") String organizationId) {
        OrganizationInfoResponse res = organizationService.fetchOrganizationInfo(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/incidents/{incidentId}/updates")
    public ResponseEntity<?> getIncidentUpdates(
            @PathVariable("incidentId") int incidentId) {
        List<IncidentUpdateResponse> updates = incidentManagementService.getIncidentUpdates(incidentId);
        return new ResponseEntity<>(updates, HttpStatus.OK);
    }
}
