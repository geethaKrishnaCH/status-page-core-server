package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Organization;
import com.easylearnz.status_page.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
    List<Role> findByOrganization(Organization organization);
}
