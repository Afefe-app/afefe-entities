package com.ocean.afefe.entities.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.tensorpoint.toolkit.tpointcore.date.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentWebhook extends BaseUUIDEntity {

    @Column(columnDefinition = "TEXT")
    private String eventData;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentGateway paymentGateway;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentWebhookStatus status;

    private Instant consumedAt;

    @Column(columnDefinition = "TEXT")
    private String failureReason;

    public static PaymentWebhook createPending(String eventData, PaymentGateway gateway){
        return PaymentWebhook.builder()
                .eventData(eventData)
                .paymentGateway(gateway)
                .status(PaymentWebhookStatus.PENDING)
                .consumedAt(DateUtils.getServerUTCNowInstant())
                .build();
    }
}
