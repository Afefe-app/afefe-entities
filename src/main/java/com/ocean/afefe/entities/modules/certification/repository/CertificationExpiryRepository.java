package com.ocean.afefe.entities.modules.certification.repository;

import com.ocean.afefe.entities.modules.certification.model.CertificationExpiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CertificationExpiryRepository extends JpaRepository<CertificationExpiry, UUID> {
}
