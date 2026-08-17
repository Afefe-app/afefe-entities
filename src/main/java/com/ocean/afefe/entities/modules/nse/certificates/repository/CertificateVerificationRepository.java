package com.ocean.afefe.entities.modules.nse.certificates.repository;

import com.ocean.afefe.entities.modules.nse.certificates.models.CertificateVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CertificateVerificationRepository extends JpaRepository<CertificateVerification, UUID> {
}
