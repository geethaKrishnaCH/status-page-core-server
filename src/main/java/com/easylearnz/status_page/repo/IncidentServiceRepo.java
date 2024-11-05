package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Incident;
import com.easylearnz.status_page.models.IncidentService;
import com.easylearnz.status_page.models.OrgService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentServiceRepo extends JpaRepository<IncidentService, Integer> {
    List<IncidentService> findByIncident(Incident incident);
}
