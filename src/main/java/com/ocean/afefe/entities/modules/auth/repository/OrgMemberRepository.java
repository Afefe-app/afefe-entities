package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrgMemberRepository extends JpaRepository<OrgMember, UUID> {
    boolean existsByUserAndOrganization(User user, Organization organization);
    boolean existsByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
    OrgMember findFirstByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
}
