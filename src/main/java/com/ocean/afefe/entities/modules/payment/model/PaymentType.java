package com.ocean.afefe.entities.modules.payment.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType implements Enumerable {
    COURSE_ENROLLMENT("Course enrollment"),
    WALLET_TOP_UP("Wallet top-up"),
    SUBSCRIPTION("Subscription"),
    OTHER("Other")
    ;
    private final String description;
}
