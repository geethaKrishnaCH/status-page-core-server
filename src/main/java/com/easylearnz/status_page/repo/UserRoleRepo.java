package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Role;
import com.easylearnz.status_page.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    @Query("SELECT ur FROM UserRole ur WHERE ur.user.userId=:userId")
    List<UserRole> findByUserId(@Param("userId") String userId);

    List<UserRole> findByRoleIn(List<Role> roles);
}
