package com.easylearnz.status_page.core.organization.controller;

import com.easylearnz.status_page.core.organization.dto.*;
import com.easylearnz.status_page.core.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/organizations")
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationRequest request) {
        CreateOrganizationResponse res = organizationService.createOrganization(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/organizations/{organizationId}/services")
    public ResponseEntity<?> getOrganizationServices(@PathVariable String organizationId) {
        List<OrganizationServiceResponse> res = organizationService.getOrganizationServices(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/public/organizations/{organizationId}/services")
    public ResponseEntity<?> getOrganizationServicesV2(@PathVariable String organizationId) {
        List<OrganizationServiceResponse> res = organizationService.getOrganizationServices(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/public/organizations")
    public ResponseEntity<?> fetchOrganizations(@RequestParam(value = "query", required = false) String query) {
        List<OrganizationResponse> res = organizationService.fetchOrganizations(query);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/public/organizations/{organizationId}")
    public ResponseEntity<?> fetchOrganizationInfo(@PathVariable("organizationId") String organizationId) {
        OrganizationInfoResponse res = organizationService.fetchOrganizationInfo(organizationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
