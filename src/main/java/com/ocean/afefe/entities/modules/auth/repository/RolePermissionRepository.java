package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.Permission;
import com.ocean.afefe.entities.modules.auth.models.Role;
import com.ocean.afefe.entities.modules.auth.models.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {

List<RolePermission> findByRole(Role role);

    List<RolePermission> findByRole_Id(UUID roleId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM RolePermission rp WHERE rp.role = :role")
    void deleteByRole(@Param("role") Role role);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM RolePermission rp WHERE rp.role.id = :roleId")
    void deleteByRole_Id(@Param("roleId") UUID roleId);

    @Query("""
            SELECT DISTINCT p.permissionKey FROM RolePermission rp
            JOIN rp.permission p
            JOIN rp.role r
            JOIN OrgMemberRole omr ON omr.role = r
            WHERE omr.orgMember.id = :orgMemberId
            """)
    List<String> findPermissionKeysByOrgMemberId(@Param("orgMemberId") UUID orgMemberId);

    @Query("""
            SELECT p.permissionKey FROM RolePermission rp
            JOIN rp.permission p
            WHERE rp.role.id = :roleId
            ORDER BY p.permissionKey ASC
            """)
    List<String> findPermissionKeysByRoleId(@Param("roleId") UUID roleId);
}
