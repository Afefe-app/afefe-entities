package com.ocean.afefe.entities.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
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

    @Column(scale = 10, precision = 38)
    private BigDecimal currentRateValue;

    private String externalReference;

    private String earningReference;

    @Column(columnDefinition = "TEXT")
    private String coreRequestLog;

    @Column(columnDefinition = "TEXT")
    private String coreResponseLog;

    public void writeCoreRequestLog(Object requestLog){
        try{
            this.setCoreRequestLog(CommonUtil.getServerMapper().writeValueAsString(requestLog));
        }catch (Exception ignored){

        }
    }

    public void writeCoreResponseLog(Object responseLog){
        try{
            this.setCoreResponseLog(CommonUtil.getServerMapper().writeValueAsString(responseLog));
        }catch (Exception ignored){}
    }
}
