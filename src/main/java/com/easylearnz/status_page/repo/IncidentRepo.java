package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Incident;
import com.easylearnz.status_page.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepo extends JpaRepository<Incident, Integer> {
    List<Incident> findByOrganizationOrderByUpdatedAtDesc(Organization organization);
}
