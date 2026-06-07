package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTestCandidate;
import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTestCandidateTestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainerProctoredTestCandidateRepository extends JpaRepository<TrainerProctoredTestCandidate, UUID> {

    List<TrainerProctoredTestCandidate> findByProctoredTest_IdOrderByCreatedAtAsc(UUID proctoredTestId);
    long countByProctoredTest_Id(UUID proctoredTestId);
    long countByProctoredTest_IdAndShortlistedTrue(UUID proctoredTestId);
    long countByProctoredTest_IdAndFlaggedIncidentCountGreaterThan(UUID proctoredTestId, Integer flaggedIncidentCount);

    Optional<TrainerProctoredTestCandidate> findByIdAndProctoredTest_Id(UUID candidateId, UUID proctoredTestId);

    @Query("""
        SELECT candidate
        FROM TrainerProctoredTestCandidate candidate
        WHERE candidate.proctoredTest.id = :proctoredTestId
          AND (:status IS NULL OR candidate.testStatus = :status)
          AND (:shortlistedOnly = false OR candidate.shortlisted = true)
          AND (:passMark IS NULL OR COALESCE(candidate.testScorePercent, 0) >= :passMark)
          AND (
            :search IS NULL OR :search = ''
            OR LOWER(COALESCE(candidate.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(candidate.email, '')) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(COALESCE(candidate.candidateCode, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          )
        """)
    Page<TrainerProctoredTestCandidate> searchByProctoredTest(
            @Param("proctoredTestId") UUID proctoredTestId,
            @Param("status") TrainerProctoredTestCandidateTestStatus status,
            @Param("shortlistedOnly") boolean shortlistedOnly,
            @Param("passMark") Integer passMark,
            @Param("search") String search,
            Pageable pageable
    );

    void deleteByProctoredTest_Id(UUID proctoredTestId);
}
