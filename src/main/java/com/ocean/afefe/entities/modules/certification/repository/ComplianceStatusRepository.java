package com.ocean.afefe.entities.modules.certification.repository;

import com.ocean.afefe.entities.modules.certification.model.ComplianceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComplianceStatusRepository extends JpaRepository<ComplianceStatus, UUID> {
}
