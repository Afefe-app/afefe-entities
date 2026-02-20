package com.ocean.afefe.entities.modules.payment.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus implements Enumerable {
    PENDING("Pending"),
    FAILED("Failed"),
    SUCCESS("Success")
    ;
    private final String description;
}
