package com.easylearnz.status_page.core.services.controller;

import com.easylearnz.status_page.core.services.dto.AddServiceRequest;
import com.easylearnz.status_page.core.services.dto.UpdateServiceRequest;
import com.easylearnz.status_page.core.services.service.ServiceManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceManagerService serviceManagerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','SERVICE_MANAGER')")
    public ResponseEntity<?> addService(
            @RequestAttribute("organizationId") String organizationId,
            @RequestBody AddServiceRequest request) {
        serviceManagerService.createService(request, organizationId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','SERVICE_MANAGER')")
    public ResponseEntity<?> updateService(@RequestBody UpdateServiceRequest request) {
        serviceManagerService.updateService(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
