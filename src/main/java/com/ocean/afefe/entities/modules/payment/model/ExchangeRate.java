package com.ocean.afefe.entities.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency from;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Currency to;

    @Column(nullable = false)
    private BigDecimal value;
}
