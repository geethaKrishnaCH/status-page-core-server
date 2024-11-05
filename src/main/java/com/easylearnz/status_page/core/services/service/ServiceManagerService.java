package com.easylearnz.status_page.core.services.service;

import com.easylearnz.status_page.core.services.dto.AddServiceRequest;
import com.easylearnz.status_page.core.services.dto.UpdateServiceRequest;
import com.easylearnz.status_page.models.OrgService;
import com.easylearnz.status_page.models.Organization;
import com.easylearnz.status_page.models.ServiceStatusHistory;
import com.easylearnz.status_page.models.enums.ServiceStatus;
import com.easylearnz.status_page.repo.OrgServiceRepo;
import com.easylearnz.status_page.repo.OrganizationRepo;
import com.easylearnz.status_page.repo.ServiceStatusHistoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceManagerService {

    private final OrgServiceRepo orgServiceRepo;
    private final OrganizationRepo organizationRepo;
    private final ServiceStatusHistoryRepo serviceStatusHistoryRepo;

    @Transactional
    public void createService(AddServiceRequest request, String organizationId) {
        Organization organization = organizationRepo.findByOrganizationId(organizationId);

        OrgService service = new OrgService();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setUrl(request.getUrl());
        service.setCategory(request.getCategory());
        service.setStatus(ServiceStatus.findStatusCode(request.getCurrentStatus()));
        service.setOrganization(organization);

        service = orgServiceRepo.save(service);

        ServiceStatusHistory history = new ServiceStatusHistory();
        history.setService(service);
        history.setStatus(ServiceStatus.findStatusCode(request.getCurrentStatus()));
        serviceStatusHistoryRepo.save(history);
    }

    @Transactional
    public void updateService(UpdateServiceRequest request) {
        OrgService service = orgServiceRepo.findById(request.getServiceId()).orElseThrow();
        service.setName(request.getName());
        service.setDescription(request.getDescription());
        service.setUrl(request.getUrl());
        service.setCategory(request.getCategory());
        if (!ServiceStatus.findStatusCode(request.getCurrentStatus()).equals(service.getStatus())) {
            service.setStatus(ServiceStatus.findStatusCode(request.getCurrentStatus()));

            ServiceStatusHistory history = new ServiceStatusHistory();
            history.setService(service);
            history.setStatus(ServiceStatus.findStatusCode(request.getCurrentStatus()));
            serviceStatusHistoryRepo.save(history);
        }

        orgServiceRepo.save(service);
    }

    @Transactional
    public void updateServiceStatus(OrgService service, ServiceStatus status) {

        if (!status.equals(service.getStatus())) {
            service.setStatus(status);

            ServiceStatusHistory history = new ServiceStatusHistory();
            history.setService(service);
            history.setStatus(status);

            serviceStatusHistoryRepo.save(history);
            orgServiceRepo.save(service);
        }

    }


}
