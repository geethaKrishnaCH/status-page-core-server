package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepo extends JpaRepository<Incident, Integer> {
}
