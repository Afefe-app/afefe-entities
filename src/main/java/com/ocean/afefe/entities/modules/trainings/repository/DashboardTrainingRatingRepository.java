package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.trainings.models.TrainingRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface DashboardTrainingRatingRepository extends JpaRepository<TrainingRating, UUID> {

    @Query("""
        SELECT AVG(tr.rating)
        FROM TrainingRating tr
        WHERE tr.training.trainer = :trainer
          AND tr.org = :org
    """)
    Double findAverageRatingByTrainerAndOrg(@Param("trainer") Trainer trainer, @Param("org") Organization org);

    @Query("""
        SELECT AVG(tr.rating)
        FROM TrainingRating tr
        WHERE tr.training.trainer = :trainer
          AND tr.org = :org
          AND tr.ratedAt >= :start
          AND tr.ratedAt < :end
    """)
    Double findAverageRatingByTrainerAndOrgAndRatedAtBetween(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            @Param("start") Instant start,
            @Param("end") Instant end
    );

    @Query("""
        SELECT tr
        FROM TrainingRating tr
        JOIN FETCH tr.user
        JOIN FETCH tr.training
        WHERE tr.training.trainer = :trainer
          AND tr.org = :org
        ORDER BY tr.ratedAt DESC
    """)
    List<TrainingRating> findRecentRatingsByTrainerAndOrg(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            Pageable pageable
    );
}
