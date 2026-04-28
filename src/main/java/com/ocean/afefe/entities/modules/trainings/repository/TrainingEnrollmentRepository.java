package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, UUID> {

    Optional<TrainingEnrollment> findByUserAndTraining(User user, Training training);

    Optional<TrainingEnrollment> findByUserAndTraining_Id(User user, UUID trainingId);

    List<TrainingEnrollment> findByUserAndOrgOrderByUpdatedAtDesc(User user, Organization org);

    Optional<TrainingEnrollment> findByIdAndUser_Id(UUID enrollmentId, UUID userId);
}
