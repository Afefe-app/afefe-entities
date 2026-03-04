package com.ocean.afefe.entities.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstructorPayoutLog extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    private Instructor instructor;

    @ManyToOne(optional = false)
    private BankAccount bankAccount;

    @Column(nullable = false)
    private BigDecimal payoutAmount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency payoutCurrency;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PayoutStatus status = PayoutStatus.PENDING;
}
