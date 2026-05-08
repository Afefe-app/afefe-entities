package com.ocean.afefe.entities.modules.talentpool.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.talentpool.models.OrgTalentShortlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrgTalentShortlistRepository extends JpaRepository<OrgTalentShortlist, UUID> {

    long countByOrganization(Organization organization);

    boolean existsByOrganizationAndTalentUser_Id(Organization organization, UUID talentUserId);

    void deleteByOrganizationAndTalentUser_Id(Organization organization, UUID talentUserId);

    @Query("""
            SELECT ots.talentUser.id
            FROM OrgTalentShortlist ots
            WHERE ots.organization = :organization
              AND ots.talentUser.id IN :userIds
            """)
    Set<UUID> findTalentUserIdsByOrganizationAndTalentUserIdIn(
            @Param("organization") Organization organization,
            @Param("userIds") Collection<UUID> userIds);
}
