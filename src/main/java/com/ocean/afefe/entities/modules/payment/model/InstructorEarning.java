package com.ocean.afefe.entities.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstructorEarning extends BaseUUIDEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Instructor instructor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CourseEnrollmentPayment enrollmentPayment;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EarningStatus status = EarningStatus.PENDING;

    @Builder.Default
    private BigDecimal baseEarning = BigDecimal.ZERO;

    private BigDecimal currentAvailableBalance = BigDecimal.ZERO;

    private BigDecimal currentTotalDeposits = BigDecimal.ZERO;

    private BigDecimal currentTotalWithdrawal = BigDecimal.ZERO;

    private Instant settledAt;

    @Column(columnDefinition = "TEXT")
    private String settlementFailureReason;

    @Enumerated(value = EnumType.STRING)
    private Currency baseCurrency;

    @Enumerated(value = EnumType.STRING)
    private Currency paymentCurrency;

    @Enumerated(value = EnumType.STRING)
    private Currency userCountryCurrency;

    private BigDecimal currentRateValue;
}
