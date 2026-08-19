package com.ocean.afefe.entities.modules.nse.onboarding.repository;

import com.ocean.afefe.entities.modules.nse.onboarding.models.UserIndustryInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserIndustryInterestRepository extends JpaRepository<UserIndustryInterest, UUID> {

    @Query("""
            SELECT uii FROM NseUserIndustryInterest uii
            JOIN FETCH uii.industry i
            WHERE uii.user.id = :userId AND uii.org.id = :orgId
            ORDER BY i.name ASC
            """)
    List<UserIndustryInterest> findByUserAndOrg(@Param("userId") UUID userId, @Param("orgId") UUID orgId);

    long countByUser_IdAndOrg_Id(UUID userId, UUID orgId);

    @Modifying
    @Query("DELETE FROM NseUserIndustryInterest uii WHERE uii.user.id = :userId AND uii.org.id = :orgId")
    void deleteByUserAndOrg(@Param("userId") UUID userId, @Param("orgId") UUID orgId);
}
