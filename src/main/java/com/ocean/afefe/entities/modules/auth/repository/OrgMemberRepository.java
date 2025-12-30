package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrgMemberRepository extends JpaRepository<OrgMember, UUID> {
    boolean existsByUserAndOrganization(User user, Organization organization);
    boolean existsByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
    OrgMember findFirstByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
}
