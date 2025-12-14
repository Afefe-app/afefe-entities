package com.ocean.afefe.entities.modules.auth.models;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserOtpAction implements Enumerable {

    EMAIL_VERIFICATION("Email verification"),
    FORGOT_PASSWORD("Forget password"),
    ;
    private final String description;
}
