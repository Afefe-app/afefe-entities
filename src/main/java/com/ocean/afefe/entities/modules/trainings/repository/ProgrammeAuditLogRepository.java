package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.ProgrammeAuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgrammeAuditLogRepository extends JpaRepository<ProgrammeAuditLog, UUID> {

    List<ProgrammeAuditLog> findByTraining_IdAndOrg_IdOrderByActionAtDesc(UUID trainingId, UUID orgId);

    Page<ProgrammeAuditLog> findByTraining_IdAndOrg_IdOrderByActionAtDesc(UUID trainingId, UUID orgId, Pageable pageable);

    long countByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId);
}
