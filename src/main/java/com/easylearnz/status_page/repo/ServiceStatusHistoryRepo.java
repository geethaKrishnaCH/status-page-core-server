package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.ServiceStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceStatusHistoryRepo extends JpaRepository<ServiceStatusHistory, Integer> {
}
