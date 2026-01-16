package com.ocean.afefe.entities.domain.event;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType implements Enumerable {

    USER_SIGNED_UP("user-signed-up", "User has signed up"),
    USER_LOGIN("user-login", "User login event"),
    SEND_OTP("send-otp", "Send otp"),
    USER_RESET_PASSWORD("user-reset-password", "User reset password"),

    INSTRUCTOR_CREATED("instructor-created", "A new instructor has been created")
    ;

    private final String key;
    private final String description;
}
