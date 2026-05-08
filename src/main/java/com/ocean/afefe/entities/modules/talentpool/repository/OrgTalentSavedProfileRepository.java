package com.ocean.afefe.entities.modules.talentpool.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.talentpool.models.OrgTalentSavedProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrgTalentSavedProfileRepository extends JpaRepository<OrgTalentSavedProfile, UUID> {

    long countByOrganization(Organization organization);

    boolean existsByOrganizationAndTalentUser_Id(Organization organization, UUID talentUserId);

    void deleteByOrganizationAndTalentUser_Id(Organization organization, UUID talentUserId);

    @Query("""
            SELECT osp.talentUser.id
            FROM OrgTalentSavedProfile osp
            WHERE osp.organization = :organization
              AND osp.talentUser.id IN :userIds
            """)
    Set<UUID> findTalentUserIdsByOrganizationAndTalentUserIdIn(
            @Param("organization") Organization organization,
            @Param("userIds") Collection<UUID> userIds);
}
