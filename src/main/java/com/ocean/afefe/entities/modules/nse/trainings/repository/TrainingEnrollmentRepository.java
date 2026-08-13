package com.ocean.afefe.entities.modules.nse.trainings.repository;
import com.ocean.afefe.entities.modules.nse.auth.models.Organization;

import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.ocean.afefe.entities.modules.nse.enrollments.models.EnrollmentStatus;
import com.ocean.afefe.entities.modules.nse.trainings.models.Training;
import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, UUID> {
    Optional<TrainingEnrollment> findByUserAndTraining_Id(User user, UUID trainingId);
    Optional<TrainingEnrollment> findByUserAndTraining(User user, Training training);
    Optional<TrainingEnrollment> findByIdAndUser_Id(UUID enrollmentId, UUID userId);
    long countByOrg_IdAndTraining_Id(UUID orgId, UUID trainingId);
    List<TrainingEnrollment> findByUserAndOrgOrderByUpdatedAtDesc(User user, Organization org);
    List<TrainingEnrollment> findByUserAndOrgAndStatusInOrderByUpdatedAtDesc(
            User user, Organization org, List<EnrollmentStatus> statuses);
    List<TrainingEnrollment> findByOrg_IdAndStatusOrderByUpdatedAtDesc(UUID orgId, EnrollmentStatus status);
}
