package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTest;
import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainerProctoredTestRepository extends JpaRepository<TrainerProctoredTest, UUID> {

    Optional<TrainerProctoredTest> findByIdAndTrainerAndOrg(UUID id, Trainer trainer, Organization org);

    @Query("""
        SELECT test
        FROM TrainerProctoredTest test
        WHERE test.trainer = :trainer
          AND test.org = :org
          AND (:status IS NULL OR test.status = :status)
          AND (
            :search IS NULL OR :search = ''
            OR LOWER(test.testName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(test.programmeId) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(test.testType) LIKE LOWER(CONCAT('%', :search, '%'))
          )
        """)
    Page<TrainerProctoredTest> searchByTrainerAndOrg(
            @Param("trainer") Trainer trainer,
            @Param("org") Organization org,
            @Param("status") TrainerProctoredTestStatus status,
            @Param("search") String search,
            Pageable pageable
    );
}
