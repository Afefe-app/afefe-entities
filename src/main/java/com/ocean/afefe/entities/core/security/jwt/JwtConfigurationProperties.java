package com.ocean.afefe.entities.core.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(value = "jwt.security.properties")
public class JwtConfigurationProperties {

    private String jwtKey = "j3H5Ld5nYmGWyULy6xwpOgfSH++NgKXnJMq20vpfd+8=t";
    private String idTokenKey = "idToken";
    private String appUserTokenInHr = "24";
    private String idTokenExpiryInMin = "30";
    private String adminUserTokenExpirationInMin = "30";
    private String subjectEncryptionKey = "77T18925x42783H7508302949Q618671";
}
