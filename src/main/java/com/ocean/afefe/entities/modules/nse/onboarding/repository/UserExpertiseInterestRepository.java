package com.ocean.afefe.entities.modules.nse.onboarding.repository;

import com.ocean.afefe.entities.modules.nse.onboarding.models.UserExpertiseInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserExpertiseInterestRepository extends JpaRepository<UserExpertiseInterest, UUID> {

    @Query("""
            SELECT uei FROM UserExpertiseInterest uei
            JOIN FETCH uei.expertise e
            WHERE uei.user.id = :userId AND uei.org.id = :orgId
            ORDER BY e.name ASC
            """)
    List<UserExpertiseInterest> findByUserAndOrg(@Param("userId") UUID userId, @Param("orgId") UUID orgId);

    long countByUser_IdAndOrg_Id(UUID userId, UUID orgId);

    @Modifying
    @Query("DELETE FROM UserExpertiseInterest uei WHERE uei.user.id = :userId AND uei.org.id = :orgId")
    void deleteByUserAndOrg(@Param("userId") UUID userId, @Param("orgId") UUID orgId);
}
