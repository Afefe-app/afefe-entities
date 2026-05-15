package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumStatus;
import com.ocean.afefe.entities.modules.trainings.models.TrainerCurriculumSubmission;
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
public interface TrainerCurriculumSubmissionRepository extends JpaRepository<TrainerCurriculumSubmission, UUID> {

    Optional<TrainerCurriculumSubmission> findByIdAndTrainerAndOrg(UUID id, Trainer trainer, Organization org);

    Optional<TrainerCurriculumSubmission> findFirstByTrainerAndOrgOrderBySubmittedAtDesc(Trainer trainer, Organization org);

    long countByTrainerAndOrg(Trainer trainer, Organization org);

    long countByTrainerAndOrgAndSubmittedAtBetween(Trainer trainer, Organization org, Instant start, Instant end);

    long countByTrainerAndOrgAndStatus(Trainer trainer, Organization org, TrainerCurriculumStatus status);

    long countByTrainerAndOrgAndStatusAndSubmittedAtBetween(
            Trainer trainer,
            Organization org,
            TrainerCurriculumStatus status,
            Instant start,
            Instant end
    );

    boolean existsByCurriculumCodeAndOrg(String curriculumCode, Organization org);

    @Query("""
        SELECT submission
        FROM TrainerCurriculumSubmission submission
        WHERE submission.trainer = :trainer
          AND submission.org = :org
          AND submission.submittedAt >= :start
          AND submission.submittedAt < :end
          AND (
            :search IS NULL OR :search = ''
            OR LOWER(submission.curriculumCode) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(submission.title) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(submission.programmeId) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(submission.category) LIKE LOWER(CONCAT('%', :search, '%'))
          )
        """)
    Page<TrainerCurriculumSubmission> searchByTrainerAndOrgAndSubmittedAtBetween(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            @Param("start") Instant start,
            @Param("end") Instant end,
            @Param("search") String search,
            Pageable pageable
    );
}
