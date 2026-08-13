package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.OrgMemberRole;
import com.ocean.afefe.entities.modules.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrgMemberRoleRepository extends JpaRepository<OrgMemberRole, UUID> {

    List<OrgMemberRole> findByOrgMember(OrgMember orgMember);

    void deleteByOrgMember(OrgMember orgMember);

    void deleteByRole(Role role);

    boolean existsByOrgMemberAndRole(OrgMember orgMember, Role role);

    long countByOrgMember(OrgMember orgMember);

    long countByRole_Id(UUID roleId);

    @Query("""
            SELECT omr FROM OrgMemberRole omr
            JOIN FETCH omr.orgMember om
            JOIN FETCH om.user
            JOIN omr.role r
            WHERE r.id IN :roleIds
            ORDER BY r.id, om.joinedAt DESC
            """)
    List<OrgMemberRole> findMembersByRoleIds(@Param("roleIds") Collection<UUID> roleIds);
}
