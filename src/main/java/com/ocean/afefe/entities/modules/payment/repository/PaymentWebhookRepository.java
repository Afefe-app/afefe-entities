package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.payment.model.PaymentWebhook;
import com.ocean.afefe.entities.modules.payment.model.PaymentWebhookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentWebhookRepository extends JpaRepository<PaymentWebhook, UUID> {

    Page<PaymentWebhook> findAllByStatus(PaymentWebhookStatus status, Pageable pageable);
}
