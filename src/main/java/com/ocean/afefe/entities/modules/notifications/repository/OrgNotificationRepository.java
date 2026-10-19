package com.ocean.afefe.entities.modules.notifications.repository;

import com.ocean.afefe.entities.modules.notifications.model.OrgNotification;
import com.ocean.afefe.entities.modules.notifications.model.OrgNotificationChannel;
import com.ocean.afefe.entities.modules.notifications.model.OrgNotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrgNotificationRepository
        extends JpaRepository<OrgNotification, UUID>, JpaSpecificationExecutor<OrgNotification> {

    Optional<OrgNotification> findByIdAndOrganization_Id(UUID id, UUID organizationId);

    @Query(
            value = """
            SELECT n FROM OrgNotification n
            JOIN FETCH n.createdBy
            WHERE n.organization.id = :organizationId
              AND (:status IS NULL OR n.status = :status)
              AND (:channel IS NULL OR n.channel = :channel)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(n.bodyHtml) LIKE LOWER(CONCAT('%', :search, '%')))
            """,
            countQuery = """
            SELECT COUNT(n) FROM OrgNotification n
            WHERE n.organization.id = :organizationId
              AND (:status IS NULL OR n.status = :status)
              AND (:channel IS NULL OR n.channel = :channel)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(n.bodyHtml) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<OrgNotification> findByOrganizationFiltered(
            @Param("organizationId") UUID organizationId,
            @Param("status") OrgNotificationStatus status,
            @Param("channel") OrgNotificationChannel channel,
            @Param("search") String search,
            Pageable pageable);

    @Query("""
            SELECT n FROM OrgNotification n
            WHERE n.status = :status
              AND n.scheduledAt IS NOT NULL
              AND n.scheduledAt <= :now
            ORDER BY n.scheduledAt ASC
            """)
    List<OrgNotification> findDueScheduled(
            @Param("status") OrgNotificationStatus status,
            @Param("now") Instant now,
            Pageable pageable);

    @Query("""
            SELECT DISTINCT n FROM OrgNotification n
            LEFT JOIN FETCH n.recipients r
            LEFT JOIN FETCH r.user
            JOIN FETCH n.organization
            JOIN FETCH n.createdBy
            WHERE n.id = :id
            """)
    Optional<OrgNotification> findByIdWithRecipientsAndUsers(@Param("id") UUID id);

    @Query("""
            SELECT DISTINCT n FROM OrgNotification n
            LEFT JOIN FETCH n.recipients r
            LEFT JOIN FETCH r.user
            JOIN FETCH n.organization
            JOIN FETCH n.createdBy
            WHERE n.id = :id AND n.organization.id = :organizationId
            """)
    Optional<OrgNotification> findByIdAndOrganizationIdWithDetails(
            @Param("id") UUID id,
            @Param("organizationId") UUID organizationId);
}
