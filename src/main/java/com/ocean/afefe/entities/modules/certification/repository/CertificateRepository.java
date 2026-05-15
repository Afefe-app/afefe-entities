package com.ocean.afefe.entities.modules.certification.repository;

import com.ocean.afefe.entities.modules.certification.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {

    @Query("SELECT c FROM Certificate c JOIN FETCH c.track t WHERE c.user.id = :userId ORDER BY c.issuedAt DESC")
    List<Certificate> findByUser_IdWithTrackOrderByIssuedAtDesc(@Param("userId") UUID userId);
}
