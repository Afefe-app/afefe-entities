package com.ocean.afefe.entities.modules.certification.repository;

import com.ocean.afefe.entities.modules.certification.model.CertificateVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CertificateVerificationRepository extends JpaRepository<CertificateVerification, UUID> {
}
