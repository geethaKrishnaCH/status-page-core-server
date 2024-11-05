package com.easylearnz.status_page.core.incident.controller;

import com.easylearnz.status_page.core.incident.dto.AddIncidentRequest;
import com.easylearnz.status_page.core.incident.dto.IncidentUpdateResponse;
import com.easylearnz.status_page.core.incident.dto.UpdateIncidentRequest;
import com.easylearnz.status_page.core.incident.service.IncidentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentManagementService incidentManagementService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','SERVICE_MANAGER')")
    public ResponseEntity<?> addIncident(
            @RequestAttribute("organizationId") String organizationId,
            @RequestAttribute("userId") String userId,
            @RequestBody AddIncidentRequest req) {
        incidentManagementService.addIncident(req, organizationId, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','SERVICE_MANAGER')")
    public ResponseEntity<?> updateIncident(
            @RequestAttribute("userId") String userId,
            @RequestBody UpdateIncidentRequest req) {
        incidentManagementService.updateIncident(req, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{incidentId}/updates")
    public ResponseEntity<?> getIncidentUpdates(
            @PathVariable("incidentId") int incidentId) {
        List<IncidentUpdateResponse> updates = incidentManagementService.getIncidentUpdates(incidentId);
        return new ResponseEntity<>(updates, HttpStatus.OK);
    }
}
