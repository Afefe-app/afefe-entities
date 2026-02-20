package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.payment.model.PaymentStatus;
import com.ocean.afefe.entities.modules.payment.model.PaymentTransaction;
import com.ocean.afefe.entities.modules.payment.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID>, QuerydslPredicateExecutor<PaymentTransaction> {

    Optional<PaymentTransaction> findByInternalReference(String internalReference);

    Optional<PaymentTransaction> findByIdempotencyKey(String idempotencyKey);

    List<PaymentTransaction> findByUserOrderByCreatedAtDesc(User user);

    List<PaymentTransaction> findByOrganizationOrderByCreatedAtDesc(Organization organization);

    List<PaymentTransaction> findByUserAndStatus(User user, PaymentStatus status);

    List<PaymentTransaction> findByUserAndPaymentTypeOrderByCreatedAtDesc(User user, PaymentType paymentType);
    PaymentTransaction findTopByUserAndEntityIdOrderByCreatedAtDesc(User user, UUID entityId);
}
