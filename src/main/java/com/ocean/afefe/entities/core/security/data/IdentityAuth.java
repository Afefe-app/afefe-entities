package com.ocean.afefe.entities.core.security.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityAuth {

    private String accessToken;
    private String type = "Bearer";
    private String tokenIssuedAt;
    private String tokenExpiredAt;
}
