package com.ocean.afefe.entities.common;

import com.tensorpoint.toolkit.tpointcore.commons.EnumData;
import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SystemParamKeys implements Enumerable {

    USER_OTP_EXPIRATION_IN_MIN("Length of time otp is to expire", "10");
    private final String description;
    private final String defaultValue;
}
