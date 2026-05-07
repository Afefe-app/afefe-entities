package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingRatingRepository extends JpaRepository<TrainingRating, UUID> {

    Page<TrainingRating> findByTrainingOrderByRatedAtDesc(Training training, Pageable pageable);

    Optional<TrainingRating> findByUser_IdAndTraining_Id(UUID userId, UUID trainingId);

    @Query("SELECT AVG(tr.rating) FROM TrainingRating tr WHERE tr.org = :org AND tr.ratedAt <= :date")
    Double getAverageRatingByOrgAndRatedAtToDate(@Param("org") Organization org, @Param("date") Instant date);
}
