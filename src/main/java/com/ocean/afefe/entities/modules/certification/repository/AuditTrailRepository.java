package com.ocean.afefe.entities.modules.certification.repository;

import com.ocean.afefe.entities.modules.certification.model.AuditTrail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface AuditTrailRepository extends JpaRepository<AuditTrail, UUID>, JpaSpecificationExecutor<AuditTrail> {

    @Query(
            value = """
            SELECT a FROM AuditTrail a
            LEFT JOIN FETCH a.user u
            WHERE (:userId IS NULL OR u.id = :userId)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(a.action, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(a.objectType, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:fromInstant IS NULL OR a.createdAt >= :fromInstant)
              AND (:toInstant IS NULL OR a.createdAt < :toInstant)
            """,
            countQuery = """
            SELECT COUNT(a) FROM AuditTrail a
            LEFT JOIN a.user u
            WHERE (:userId IS NULL OR u.id = :userId)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(a.action, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(a.objectType, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:fromInstant IS NULL OR a.createdAt >= :fromInstant)
              AND (:toInstant IS NULL OR a.createdAt < :toInstant)
            """)
    Page<AuditTrail> findAdminActivities(
            @Param("userId") UUID userId,
            @Param("search") String search,
            @Param("fromInstant") Instant fromInstant,
            @Param("toInstant") Instant toInstant,
            Pageable pageable);

    @Query(
            value = """
            SELECT a FROM AuditTrail a
            LEFT JOIN FETCH a.user u
            WHERE u.id = :userId
            ORDER BY a.createdAt DESC
            """,
            countQuery = """
            SELECT COUNT(a) FROM AuditTrail a
            WHERE a.user.id = :userId
            """)
    Page<AuditTrail> findByUser_Id(@Param("userId") UUID userId, Pageable pageable);
}
