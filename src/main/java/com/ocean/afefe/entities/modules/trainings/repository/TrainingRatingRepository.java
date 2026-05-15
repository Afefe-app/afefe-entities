package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingRatingRepository extends JpaRepository<TrainingRating, UUID> {

    Page<TrainingRating> findByTrainingOrderByRatedAtDesc(Training training, Pageable pageable);

    Page<TrainingRating> findByTraining_TrainerAndOrgOrderByRatedAtDesc(Trainer trainer, Organization org, Pageable pageable);

    Optional<TrainingRating> findByUser_IdAndTraining_Id(UUID userId, UUID trainingId);

    @Query("SELECT AVG(tr.rating) FROM TrainingRating tr WHERE tr.org = :org AND tr.ratedAt <= :date")
    Double getAverageRatingByOrgAndRatedAtToDate(@Param("org") Organization org, @Param("date") Instant date);

    @Query("SELECT AVG(gr.rating) FROM TrainingRating gr WHERE gr.org.id = :orgId")
    Double getAverageRatingByOrgId(@Param("orgId") UUID orgId);

    @Query("""
            SELECT AVG(gr.rating)
            FROM TrainingRating gr
            WHERE gr.org.id = :orgId
              AND gr.ratedAt >= :start
              AND gr.ratedAt < :end
            """)
    Double getAverageRatingByOrgIdAndRatedAtBetween(
            @Param("orgId") UUID orgId,
            @Param("start") Instant start,
            @Param("end") Instant end);

    @Query("""
            SELECT tr.user.id, AVG(gr.rating)
            FROM TrainingRating gr
            JOIN gr.training t
            JOIN t.trainer tr
            WHERE tr.user.id IN :userIds
              AND tr.org.id = :orgId
            GROUP BY tr.user.id
            """)
    List<Object[]> getAverageRatingGroupedByTrainerUserIdsForOrg(
            @Param("userIds") Collection<UUID> userIds,
            @Param("orgId") UUID orgId);

    @Query("""
            SELECT tr.user.id, AVG(gr.rating)
            FROM TrainingRating gr
            JOIN gr.training t
            JOIN t.trainer tr
            WHERE tr.org.id = :orgId
            GROUP BY tr.user.id
            ORDER BY AVG(gr.rating) DESC
            """)
    List<Object[]> findTopTrainerUserIdsByAverageRatingForOrg(@Param("orgId") UUID orgId, Pageable pageable);

    @Query("""
            SELECT tr.user.id, COUNT(t)
            FROM Training t
            JOIN t.trainer tr
            WHERE tr.org.id = :orgId
            GROUP BY tr.user.id
            ORDER BY COUNT(t) DESC
            """)
    List<Object[]> findTopTrainerUserIdsByProgrammeCountForOrg(@Param("orgId") UUID orgId, Pageable pageable);

    @Query("""
            SELECT gr.org.id, AVG(gr.rating)
            FROM TrainingRating gr
            WHERE gr.org.id IN :orgIds
            GROUP BY gr.org.id
            """)
    List<Object[]> getAverageRatingGroupedByOrgIds(@Param("orgIds") Collection<UUID> orgIds);

    @Query("SELECT AVG(gr.rating) FROM TrainingRating gr WHERE gr.org.id <> :excludedOrgId")
    Double getAverageRatingForAllOrgsExcept(@Param("excludedOrgId") UUID excludedOrgId);

    @Query("""
            SELECT AVG(gr.rating)
            FROM TrainingRating gr
            WHERE gr.org.id <> :excludedOrgId
              AND gr.ratedAt >= :start
              AND gr.ratedAt < :end
            """)
    Double getAverageRatingForAllOrgsExceptAndRatedBetween(
            @Param("excludedOrgId") UUID excludedOrgId,
            @Param("start") Instant start,
            @Param("end") Instant end);
}

