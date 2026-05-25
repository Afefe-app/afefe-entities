package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainerProctoredTestCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainerProctoredTestCandidateRepository extends JpaRepository<TrainerProctoredTestCandidate, UUID> {

    List<TrainerProctoredTestCandidate> findByProctoredTest_IdOrderByCreatedAtAsc(UUID proctoredTestId);
    long countByProctoredTest_Id(UUID proctoredTestId);

    void deleteByProctoredTest_Id(UUID proctoredTestId);
}
