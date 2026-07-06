package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.payment.model.PaymentStatus;
import com.ocean.afefe.entities.modules.payment.model.PaymentTransaction;
import com.ocean.afefe.entities.modules.payment.model.PaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID>, QuerydslPredicateExecutor<PaymentTransaction> {

    Optional<PaymentTransaction> findByInternalReference(String internalReference);

    Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey);

    List<PaymentTransaction> findByUserOrderByCreatedAtDesc(User user);

    Page<PaymentTransaction> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    List<PaymentTransaction> findByOrganizationOrderByCreatedAtDesc(Organization organization);

    List<PaymentTransaction> findByUserAndStatus(User user, PaymentStatus status);
    List<PaymentTransaction> findAllByStatus(PaymentStatus status);

    List<PaymentTransaction> findByUserAndPaymentTypeOrderByCreatedAtDesc(User user, PaymentType paymentType);
    PaymentTransaction findTopByUserAndEntityIdOrderByCreatedAtDesc(User user, UUID entityId);

    long countByStatus(PaymentStatus status);

    @Query("SELECT COALESCE(SUM(pt.amount), 0) FROM PaymentTransaction pt WHERE pt.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") PaymentStatus status);

    @Query("""
            SELECT COALESCE(SUM(pt.amount), 0) FROM PaymentTransaction pt
            WHERE pt.status = :status AND pt.createdAt >= :from AND pt.createdAt < :to
            """)
    BigDecimal sumAmountByStatusAndCreatedAtBetween(
            @Param("status") PaymentStatus status,
            @Param("from") Instant from,
            @Param("to") Instant to);

    @Query("""
            SELECT COALESCE(SUM(pt.amount), 0) FROM PaymentTransaction pt
            WHERE pt.organization.id = :organizationId AND pt.status = :status
            """)
    BigDecimal sumAmountByOrganizationIdAndStatus(
            @Param("organizationId") UUID organizationId, @Param("status") PaymentStatus status);

    @Query("""
            SELECT COUNT(pt) FROM PaymentTransaction pt
            WHERE pt.organization.id = :organizationId AND pt.status = :status
            """)
    long countByOrganizationIdAndStatus(
            @Param("organizationId") UUID organizationId, @Param("status") PaymentStatus status);

    @Query("""
            SELECT COALESCE(SUM(pt.amount), 0) FROM PaymentTransaction pt
            WHERE pt.organization.id = :organizationId AND pt.status = :status
              AND pt.createdAt >= :from AND pt.createdAt < :to
            """)
    BigDecimal sumAmountByOrganizationIdAndStatusAndCreatedAtBetween(
            @Param("organizationId") UUID organizationId,
            @Param("status") PaymentStatus status,
            @Param("from") Instant from,
            @Param("to") Instant to);

    @Query("""
            SELECT pt.paymentType AS paymentType,
                   COALESCE(SUM(pt.amount), 0) AS totalAmount,
                   COUNT(pt) AS transactionCount
            FROM PaymentTransaction pt
            WHERE pt.status = :status
            GROUP BY pt.paymentType
            """)
    List<PaymentTypeRevenueAggregate> sumByPaymentTypeForStatus(@Param("status") PaymentStatus status);

    @Query("""
            SELECT pt.paymentType AS paymentType,
                   COALESCE(SUM(pt.amount), 0) AS totalAmount,
                   COUNT(pt) AS transactionCount
            FROM PaymentTransaction pt
            WHERE pt.organization.id = :organizationId AND pt.status = :status
            GROUP BY pt.paymentType
            """)
    List<PaymentTypeRevenueAggregate> sumByPaymentTypeForOrganization(
            @Param("organizationId") UUID organizationId, @Param("status") PaymentStatus status);

    @Query(
            value = """
            SELECT pt FROM PaymentTransaction pt
            LEFT JOIN FETCH pt.user u
            LEFT JOIN FETCH pt.organization o
            WHERE (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(pt.internalReference, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(pt.customerEmail, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(o.name, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:status IS NULL OR pt.status = :status)
            """,
            countQuery = """
            SELECT COUNT(pt) FROM PaymentTransaction pt
            LEFT JOIN pt.user u
            LEFT JOIN pt.organization o
            WHERE (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(pt.internalReference, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(pt.customerEmail, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(o.name, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:status IS NULL OR pt.status = :status)
            """)
    Page<PaymentTransaction> searchForGovernmentReport(
            @Param("search") String search,
            @Param("status") PaymentStatus status,
            Pageable pageable);

    @Query(
            value = """
            SELECT pt FROM PaymentTransaction pt
            LEFT JOIN FETCH pt.user u
            LEFT JOIN FETCH pt.organization o
            WHERE (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(pt.internalReference, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(pt.customerEmail, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(o.name, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:status IS NULL OR pt.status = :status)
              AND (:paymentType IS NULL OR pt.paymentType = :paymentType)
              AND (:organizationId IS NULL OR o.id = :organizationId)
              AND pt.createdAt >= :from
              AND pt.createdAt < :to
            """,
            countQuery = """
            SELECT COUNT(pt) FROM PaymentTransaction pt
            LEFT JOIN pt.user u
            LEFT JOIN pt.organization o
            WHERE (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(pt.internalReference, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(pt.customerEmail, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(o.name, '')) LIKE LOWER(CONCAT('%', :search, '%')))
              AND (:status IS NULL OR pt.status = :status)
              AND (:paymentType IS NULL OR pt.paymentType = :paymentType)
              AND (:organizationId IS NULL OR o.id = :organizationId)
              AND pt.createdAt >= :from
              AND pt.createdAt < :to
            """)
    Page<PaymentTransaction> searchForGovernmentFinance(
            @Param("search") String search,
            @Param("status") PaymentStatus status,
            @Param("paymentType") PaymentType paymentType,
            @Param("organizationId") UUID organizationId,
            @Param("from") Instant from,
            @Param("to") Instant to,
            Pageable pageable);
}
