package com.ocean.afefe.entities.core.security;

import com.ocean.afefe.entities.core.security.otp.OtpValidationServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        OtpValidationServiceImpl.class
})
@Configuration
public class SecurityModule {
}
