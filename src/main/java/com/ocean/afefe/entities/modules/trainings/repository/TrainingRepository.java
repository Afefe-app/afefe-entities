package com.ocean.afefe.entities.modules.trainings.repository;
import com.ocean.afefe.entities.modules.auth.models.Organization;

import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.trainings.models.TrainingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingRepository extends JpaRepository<Training, UUID>, JpaSpecificationExecutor<Training> {

    Optional<Training> findByIdAndOrg_Id(UUID id, UUID orgId);

    Optional<Training> findByIdAndTrainerAndOrg_Id(UUID id, Trainer trainer, UUID orgId);

    List<Training> findAllByTrainerAndOrg_IdOrderByTitleAsc(Trainer trainer, UUID orgId);

    long countByOrg_Id(UUID orgId);

    long countByOrg_IdAndCreatedAtBetween(UUID orgId, Instant start, Instant end);

    boolean existsByProgrammeIdAndTrainerAndOrg(String programmeId, Trainer trainer, Organization org);

    @Query("""
            SELECT t
            FROM Training t
            WHERE t.trainer = :trainer
              AND t.org = :org
              AND (:status IS NULL OR t.status = :status)
              AND (
                :search IS NULL OR :search = ''
                OR LOWER(COALESCE(t.title, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(t.programmeId, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            """)
    Page<Training> searchByTrainerAndOrg(@Param("trainer") Trainer trainer,
                                         @Param("org") Organization org,
                                         @Param("status") TrainingStatus status,
                                         @Param("search") String search,
                                         Pageable pageable);

    @Query("""
            SELECT tr.user.id, COUNT(t)
            FROM Training t
            JOIN t.trainer tr
            WHERE tr.user.id IN :userIds
              AND tr.org.id = :orgId
            GROUP BY tr.user.id
            """)
    List<Object[]> countTrainingsGroupedByTrainerUserIdsForOrg(
            @Param("userIds") Collection<UUID> userIds,
            @Param("orgId") UUID orgId);
}
