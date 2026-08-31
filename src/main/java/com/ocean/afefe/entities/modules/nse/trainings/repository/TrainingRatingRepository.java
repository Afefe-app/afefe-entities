package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TrainingRatingRepository extends JpaRepository<TrainingRating, UUID> {

    @Query("""
            SELECT r FROM NseTrainingRating r
            JOIN FETCH r.training t
            WHERE r.user.id = :userId AND r.org.id = :orgId
            ORDER BY r.createdAt DESC
            """)
    List<TrainingRating> findByUserAndOrgOrderByCreatedAtDesc(
            @Param("userId") UUID userId,
            @Param("orgId") UUID orgId
    );
}
