package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OrgMemberRepository extends JpaRepository<OrgMember, UUID> {
    boolean existsByUserAndOrganization(User user, Organization organization);
    boolean existsByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
    OrgMember findFirstByUser_EmailAddressAndOrganization(String emailAddress, Organization organization);
    long countByOrganization(Organization organization);

    @Query("SELECT COUNT(om) FROM OrgMember om WHERE om.organization = :org AND om.joinedAt IS NOT NULL AND om.joinedAt >= :after")
    long countByOrganizationAndJoinedAtAfter(@Param("org") Organization org, @Param("after") Instant after);

    @Query("SELECT COUNT(om) FROM OrgMember om WHERE om.organization = :org AND om.joinedAt IS NOT NULL AND om.joinedAt <= :date")
    long countByOrganizationAndJoinedAtToDate(@Param("org") Organization org, @Param("date") Instant date);

    @Query("""
        SELECT DISTINCT om.user.id
        FROM OrgMember om
        WHERE om.organization = :org
          AND (
              LOWER(COALESCE(om.user.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(COALESCE(om.user.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          )
    """)
    List<UUID> findDistinctUserIdsByOrganizationAndSearch(
            @Param("org") Organization org,
            @Param("search") String search
    );
}
