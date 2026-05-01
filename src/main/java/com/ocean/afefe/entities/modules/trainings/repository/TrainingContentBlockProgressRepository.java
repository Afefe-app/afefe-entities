package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingContentBlock;
import com.ocean.afefe.entities.modules.trainings.models.TrainingContentBlockProgress;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingContentBlockProgressRepository extends JpaRepository<TrainingContentBlockProgress, UUID> {

    List<TrainingContentBlockProgress> findByEnrollment(TrainingEnrollment enrollment);

    Optional<TrainingContentBlockProgress> findByEnrollmentAndBlock(
            TrainingEnrollment enrollment, TrainingContentBlock block);
}
