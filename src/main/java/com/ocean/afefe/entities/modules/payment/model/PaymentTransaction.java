package com.ocean.afefe.entities.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "payment_transactions")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class PaymentTransaction extends BaseUUIDEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private PaymentGateway gateway;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private PaymentType paymentType;

    private UUID entityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;

    @Column(nullable = false, length = 1000)
    private String internalReference;

    @Column(length = 1000)
    private String gatewayReference;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "VARCHAR")
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(nullable = false, scale = 2)
    private BigDecimal amount;

    private Integer minorAmounts; // holds cents, kobo or any other minor currency amounts

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR")
    private Currency currency;

    @Column(length = 1000)
    private String description;

    @Column(length = 500)
    private String customerEmail;

    private Instant paidAt;

    private Instant settledAt;

    @Column(length = 100)
    private String gatewayErrorCode;

    @Column(columnDefinition = "TEXT")
    private String gatewayErrorMessage;

    @Column(unique = true, columnDefinition = "TEXT")
    private String idempotencyKey;

    @Column(columnDefinition = "TEXT")
    private String gatewayLogs;

    @Column(columnDefinition = "TEXT")
    private String gatewayCheckoutUrl;

    public boolean isCompleted(){
        return PaymentStatus.SUCCESS.equals(this.getStatus());
    }
}
