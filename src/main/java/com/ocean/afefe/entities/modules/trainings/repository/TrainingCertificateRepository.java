package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.TrainingCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingCertificateRepository extends JpaRepository<TrainingCertificate, UUID> {

    List<TrainingCertificate> findByUser_IdOrderByIssuedAtDesc(UUID userId);

    Optional<TrainingCertificate> findByIdAndUser_Id(UUID certificateId, UUID userId);

    Optional<TrainingCertificate> findByUser_IdAndTraining_Id(UUID userId, UUID trainingId);

    @Query("SELECT COUNT(tc) FROM TrainingCertificate tc WHERE tc.org = :org AND tc.issuedAt <= :date")
    long countByOrgAndIssuedAtToDate(@Param("org") Organization org, @Param("date") Instant date);
}
