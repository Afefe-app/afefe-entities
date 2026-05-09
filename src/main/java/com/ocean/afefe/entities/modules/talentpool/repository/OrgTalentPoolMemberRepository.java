package com.ocean.afefe.entities.modules.talentpool.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.talentpool.models.OrgTalentPoolMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrgTalentPoolMemberRepository
        extends JpaRepository<OrgTalentPoolMember, UUID>, JpaSpecificationExecutor<OrgTalentPoolMember> {

    long countByOrganization(Organization organization);

    boolean existsByOrganizationAndTalentUser_Id(Organization organization, UUID talentUserId);

    @Query("""
            SELECT otpm.talentUser.id
            FROM OrgTalentPoolMember otpm
            WHERE otpm.organization = :organization
            """)
    List<UUID> findTalentUserIdsByOrganization(@Param("organization") Organization organization);
}
