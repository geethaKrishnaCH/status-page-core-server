package com.easylearnz.status_page.core.services.service;

import com.easylearnz.status_page.core.services.dto.AddServiceRequest;
import com.easylearnz.status_page.models.OrgService;
import com.easylearnz.status_page.models.Organization;
import com.easylearnz.status_page.models.enums.ServiceStatus;
import com.easylearnz.status_page.repo.OrganizationRepo;
import com.easylearnz.status_page.repo.OrgServiceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceManagerService {

    private final OrgServiceRepo orgServiceRepo;
    private final OrganizationRepo organizationRepo;

    public void createService(AddServiceRequest request, String organizationId) {
        Organization organization = organizationRepo.findByOrganizationId(organizationId);

        OrgService service = new OrgService();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setUrl(request.getUrl());
        service.setCategory(request.getCategory());
        service.setStatus(ServiceStatus.findStatusCode(request.getCurrentStatus()));
        service.setOrganization(organization);

        orgServiceRepo.save(service);
    }
}
