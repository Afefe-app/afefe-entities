package com.ocean.afefe.entities.modules.nse.certificates.repository;

import com.ocean.afefe.entities.modules.nse.certificates.models.Certificate;
import com.ocean.afefe.entities.modules.nse.certificates.models.CertificateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificateRepository extends JpaRepository<Certificate, UUID> {

    @Query("""
            SELECT c FROM NseCertificate c
            LEFT JOIN FETCH c.training t
            WHERE c.user.id = :userId AND c.org.id = :orgId
            ORDER BY c.issuedAt DESC
            """)
    List<Certificate> findByUserAndOrgOrderByIssuedAtDesc(@Param("userId") UUID userId, @Param("orgId") UUID orgId);

    @Query("""
            SELECT c FROM NseCertificate c
            LEFT JOIN FETCH c.training t
            LEFT JOIN FETCH c.user u
            WHERE c.id = :id AND c.user.id = :userId AND c.org.id = :orgId
            """)
    Optional<Certificate> findByIdAndUserAndOrg(
            @Param("id") UUID id,
            @Param("userId") UUID userId,
            @Param("orgId") UUID orgId
    );

    @Query("""
            SELECT c FROM NseCertificate c
            LEFT JOIN FETCH c.training t
            LEFT JOIN FETCH c.user u
            WHERE lower(c.certificateNumber) = lower(:certificateNumber)
              AND c.org.id = :orgId
            """)
    Optional<Certificate> findByCertificateNumberAndOrg(
            @Param("certificateNumber") String certificateNumber,
            @Param("orgId") UUID orgId
    );

    @Query("""
            SELECT c FROM NseCertificate c
            LEFT JOIN FETCH c.training t
            LEFT JOIN FETCH c.user u
            WHERE lower(c.certificateNumber) = lower(:certificateNumber)
            """)
    Optional<Certificate> findByCertificateNumberIgnoreCase(@Param("certificateNumber") String certificateNumber);

    boolean existsByCertificateNumberIgnoreCaseAndOrg_Id(String certificateNumber, UUID orgId);

    long countByUser_IdAndOrg_IdAndStatus(UUID userId, UUID orgId, CertificateStatus status);
}
