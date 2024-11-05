package com.easylearnz.status_page.repo;

import com.easylearnz.status_page.models.Invitation;
import com.easylearnz.status_page.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepo extends JpaRepository<Invitation, Integer> {
    Invitation findByOrganizationAndInviteeAndAccepted(Organization organization, String email, boolean accepted);
}
