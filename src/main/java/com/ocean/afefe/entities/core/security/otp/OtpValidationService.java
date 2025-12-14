package com.ocean.afefe.entities.core.security.otp;

import com.ocean.afefe.entities.modules.auth.models.UserOtpAction;

public interface OtpValidationService {
    void validateOtp(String rawOtp, String emailAddress, UserOtpAction action);
}
