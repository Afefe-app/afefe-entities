package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.ocean.afefe.entities.modules.nse.trainings.models.Training;
import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingContentItem;
import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingContentProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingContentProgressRepository extends JpaRepository<TrainingContentProgress, UUID> {

    Optional<TrainingContentProgress> findByUserAndContentItem(User user, TrainingContentItem contentItem);

    List<TrainingContentProgress> findByUserAndTraining(User user, Training training);

    long countByUserAndTrainingAndCompletedTrue(User user, Training training);
}
