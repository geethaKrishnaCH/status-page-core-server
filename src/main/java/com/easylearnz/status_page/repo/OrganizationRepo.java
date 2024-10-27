package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Organization;
import com.easylearnz.status_page.organization.dto.OrganizationResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepo extends JpaRepository<Organization, Integer> {

    @Query("SELECT new com.easylearnz.status_page.organization.dto.OrganizationResponse(" +
            "o.organizationId, o.name, o.displayName" +
            ") FROM Organization o WHERE o.name LIKE :name OR o.displayName LIKE :name")
    List<OrganizationResponse> findByName(@Param("name") String name);

}
