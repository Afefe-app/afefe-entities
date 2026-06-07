package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTestCandidateViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainerProctoredTestCandidateViolationRepository extends JpaRepository<TrainerProctoredTestCandidateViolation, UUID> {

    List<TrainerProctoredTestCandidateViolation> findByCandidate_IdOrderByOccurredAtDesc(UUID candidateId);

    void deleteByCandidate_ProctoredTest_Id(UUID proctoredTestId);
}
