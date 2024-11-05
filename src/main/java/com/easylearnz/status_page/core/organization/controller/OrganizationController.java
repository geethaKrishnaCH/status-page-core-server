package com.easylearnz.status_page.core.organization.controller;

import com.easylearnz.status_page.core.organization.dto.*;
import com.easylearnz.status_page.core.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationRequest request) {
        CreateOrganizationResponse res = organizationService.createOrganization(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{organizationId}/services")
    public ResponseEntity<?> getOrganizationServices(@PathVariable String organizationId) {
        List<OrganizationServiceResponse> res = organizationService.getOrganizationServices(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{organizationId}/incidents")
    public ResponseEntity<?> getOrganizationIncidents(@PathVariable String organizationId) {
        List<OrganizationIncidentResponse> res = organizationService.getOrganizationIncidents(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
