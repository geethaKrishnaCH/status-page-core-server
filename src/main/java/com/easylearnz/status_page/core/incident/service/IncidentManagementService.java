package com.easylearnz.status_page.core.incident.service;

import com.easylearnz.status_page.core.incident.dto.AddIncidentRequest;
import com.easylearnz.status_page.models.*;
import com.easylearnz.status_page.models.enums.IncidentStatus;
import com.easylearnz.status_page.models.enums.ServiceStatus;
import com.easylearnz.status_page.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncidentManagementService {
    private final UserRepo userRepo;
    private final IncidentRepo incidentRepo;
    private final OrgServiceRepo orgServiceRepo;
    private final OrganizationRepo organizationRepo;
    private final IncidentServiceRepo incidentServiceRepo;

    public void addIncident(AddIncidentRequest req, String organizationId, String userId) {
        User user = userRepo.findByUserId(userId);
        Organization organization = organizationRepo.findByOrganizationId(organizationId);
        Incident incident = new Incident();
        incident.setAssignee(user);
        incident.setOrganization(organization);
        incident.setTitle(req.getIncidentName());
        incident.setDescription(req.getMessage());
        incident.setStatus(IncidentStatus.findStatusCode(req.getIncidentStatus()));

        incident = incidentRepo.save(incident);

        for (int serviceId : req.getServices()) {
            IncidentService incidentService = new IncidentService();
            Optional<OrgService> serviceOpt = orgServiceRepo.findById(serviceId);
            incidentService.setIncident(incident);
            incidentService.setService(serviceOpt.get());
            incidentService.setServiceStatus(ServiceStatus.findStatusCode(req.getServiceStatus()));
            incidentServiceRepo.save(incidentService);
        }
    }

}
