package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUserId(String userId);
}
