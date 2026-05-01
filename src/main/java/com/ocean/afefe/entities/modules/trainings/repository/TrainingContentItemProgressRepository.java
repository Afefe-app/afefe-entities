package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingContentItem;
import com.ocean.afefe.entities.modules.trainings.models.TrainingContentItemProgress;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingContentItemProgressRepository extends JpaRepository<TrainingContentItemProgress, UUID> {

    List<TrainingContentItemProgress> findByEnrollment(TrainingEnrollment enrollment);

    Optional<TrainingContentItemProgress> findByEnrollmentAndContentItem(
            TrainingEnrollment enrollment, TrainingContentItem contentItem);
}
