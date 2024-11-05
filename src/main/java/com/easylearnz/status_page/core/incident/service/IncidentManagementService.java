package com.easylearnz.status_page.core.incident.service;

import com.easylearnz.status_page.core.incident.dto.AddIncidentRequest;
import com.easylearnz.status_page.core.incident.dto.IncidentUpdateResponse;
import com.easylearnz.status_page.core.incident.dto.UpdateIncidentRequest;
import com.easylearnz.status_page.core.organization.dto.OrganizationIncidentResponse;
import com.easylearnz.status_page.core.services.service.ServiceManagerService;
import com.easylearnz.status_page.models.*;
import com.easylearnz.status_page.models.enums.IncidentStatus;
import com.easylearnz.status_page.models.enums.ServiceStatus;
import com.easylearnz.status_page.repo.*;
import com.easylearnz.status_page.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentManagementService {
    private final UserRepo userRepo;
    private final IncidentRepo incidentRepo;
    private final OrgServiceRepo orgServiceRepo;
    private final OrganizationRepo organizationRepo;
    private final IncidentUpdateRepo incidentUpdateRepo;
    private final IncidentServiceRepo incidentServiceRepo;
    private final ServiceManagerService serviceManagerService;

    @Transactional
    public void addIncident(AddIncidentRequest req, String organizationId, String userId) {
        User user = userRepo.findByUserId(userId);
        Organization organization = organizationRepo.findByOrganizationId(organizationId);
        Incident incident = new Incident();
        incident.setAssignee(user);
        incident.setOrganization(organization);
        incident.setTitle(req.getTitle());
        incident.setDescription(req.getDescription());
        incident.setStatus(IncidentStatus.findStatusCode(req.getStatus()));

        incident = incidentRepo.save(incident);

        IncidentUpdate incidentUpdate = new IncidentUpdate();
        incidentUpdate.setIncident(incident);
        incidentUpdate.setStatus(IncidentStatus.findStatusCode(req.getStatus()));
        incidentUpdate.setUser(user);
        incidentUpdate.setMessage(req.getMessage());
        incidentUpdateRepo.save(incidentUpdate);

        for (int serviceId : req.getSelectedServices()) {
            IncidentService incidentService = new IncidentService();
            OrgService service = orgServiceRepo.findById(serviceId).orElseThrow();
            incidentService.setIncident(incident);
            incidentService.setService(service);
            incidentService.setServiceStatus(ServiceStatus.findStatusCode(req.getServiceStatus()));
            serviceManagerService.updateServiceStatus(service, ServiceStatus.findStatusCode(req.getServiceStatus()));
            incidentServiceRepo.save(incidentService);

        }
    }

    public void updateIncident(UpdateIncidentRequest req, String userId) {
        User user = userRepo.findByUserId(userId);
        Incident incident = incidentRepo.findById(req.getId()).orElseThrow();
        incident.setDescription(req.getDescription());
        IncidentStatus newStatus = IncidentStatus.findStatusCode(req.getStatus());
        incident.setStatus(newStatus);

        List<IncidentUpdate> incidentUpdates = incidentUpdateRepo.findByIncidentOrderByCreatedAtDesc(incident);
        if (incidentUpdates.size() > 0) {
            IncidentUpdate latestIncidentUpdate = incidentUpdates.get(0);
            if (!latestIncidentUpdate.getStatus().equals(newStatus) || !req.getMessage().equalsIgnoreCase(latestIncidentUpdate.getMessage())) {
                IncidentUpdate newIncidentUpdate = new IncidentUpdate();
                newIncidentUpdate.setMessage(req.getMessage());
                newIncidentUpdate.setStatus(IncidentStatus.findStatusCode(req.getStatus()));
                newIncidentUpdate.setIncident(incident);
                newIncidentUpdate.setUser(user);
                incidentUpdateRepo.save(newIncidentUpdate);
            }
        }
        incidentRepo.save(incident);
    }

    public List<IncidentUpdateResponse> getIncidentUpdates(int incidentId) {
        Incident incident = incidentRepo.findById(incidentId).orElseThrow();
        List<IncidentUpdateResponse> incidentUpdates = incidentUpdateRepo
                .findByIncidentOrderByCreatedAtDesc(incident)
                .stream().map(incidentUpdate -> IncidentUpdateResponse
                        .builder()
                        .message(incidentUpdate.getMessage())
                        .status(incidentUpdate.getStatus().name())
                        .timestamp(DateUtil.convertDateToString(incidentUpdate.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
        return incidentUpdates;
    }
}
