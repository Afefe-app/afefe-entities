package com.ocean.afefe.entities.modules.payment.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String accountNumber;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Builder.Default
    @Column(nullable = false)
    private String customerName = StringValues.EMPTY_STRING;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency currency = Currency.USD;

    @Column(nullable = false)
    private String walletProductName;

    @Column(nullable = false)
    private String coreStatus;
}
