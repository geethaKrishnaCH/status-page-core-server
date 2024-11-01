package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.OrgService;
import com.easylearnz.status_page.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgServiceRepo extends JpaRepository<OrgService, Integer> {
    List<OrgService> findByOrganization(Organization organization);
}
