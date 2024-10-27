package com.easylearnz.status_page.organization.controller;

import com.easylearnz.status_page.organization.dto.CreateOrganizationRequest;
import com.easylearnz.status_page.organization.dto.CreateOrganizationResponse;
import com.easylearnz.status_page.organization.dto.OrganizationResponse;
import com.easylearnz.status_page.organization.service.OrganizationService;
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

    @PostMapping("/organization")
    public ResponseEntity<?> createOrganization(@RequestBody CreateOrganizationRequest request) {
        CreateOrganizationResponse res = organizationService.createOrganization(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/public/organization")
    public ResponseEntity<?> fetchOrganizations(@RequestParam(value = "query", required = false) String query) {
        List<OrganizationResponse> res = organizationService.fetchOrganizations(query);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
