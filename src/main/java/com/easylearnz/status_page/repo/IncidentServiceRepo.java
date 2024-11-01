package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.IncidentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentServiceRepo extends JpaRepository<IncidentService, Integer> {
}
