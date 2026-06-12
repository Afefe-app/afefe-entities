package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.OrgMemberRole;
import com.ocean.afefe.entities.modules.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrgMemberRoleRepository extends JpaRepository<OrgMemberRole, UUID> {

    List<OrgMemberRole> findByOrgMember(OrgMember orgMember);

    void deleteByOrgMember(OrgMember orgMember);

    void deleteByRole(Role role);

    boolean existsByOrgMemberAndRole(OrgMember orgMember, Role role);

    long countByOrgMember(OrgMember orgMember);
}
