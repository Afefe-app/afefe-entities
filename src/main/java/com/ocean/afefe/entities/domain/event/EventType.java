package com.ocean.afefe.entities.domain.event;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType implements Enumerable {

    USER_SIGNED_UP("user-signed-up", "User has signed up"),
    SEND_OTP("send-otp", "Send otp")
    ;

    private final String key;
    private final String description;
}
