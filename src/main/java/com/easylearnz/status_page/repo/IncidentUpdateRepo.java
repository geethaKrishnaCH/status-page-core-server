package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Incident;
import com.easylearnz.status_page.models.IncidentUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentUpdateRepo extends JpaRepository<IncidentUpdate, Integer> {
    List<IncidentUpdate> findByIncidentOrderByCreatedAtDesc(Incident incident);

    @Query("SELECT iu.message FROM IncidentUpdate iu WHERE iu.incident=:incident ORDER BY iu.createdAt DESC")
    List<String> findLastMessage(@Param("incident") Incident incident, Pageable pageable);
}
