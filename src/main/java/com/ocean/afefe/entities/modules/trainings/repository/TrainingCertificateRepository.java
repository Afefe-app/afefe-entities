package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.TrainingCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingCertificateRepository extends JpaRepository<TrainingCertificate, UUID> {

    List<TrainingCertificate> findByUser_IdOrderByIssuedAtDesc(UUID userId);

    Optional<TrainingCertificate> findByIdAndUser_Id(UUID certificateId, UUID userId);

    Optional<TrainingCertificate> findByUser_IdAndTraining_Id(UUID userId, UUID trainingId);
}
