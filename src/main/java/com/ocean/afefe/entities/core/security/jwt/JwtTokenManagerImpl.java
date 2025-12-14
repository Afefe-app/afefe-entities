package com.ocean.afefe.entities.core.security.jwt;

import com.ocean.afefe.entities.core.localstore.LocalParamStorage;
import com.ocean.afefe.entities.core.security.data.IdentityAuth;
import com.ocean.afefe.entities.core.security.data.JwtTokenKey;
import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import com.tensorpoint.toolkit.encryption.manager.OmnixEncryptionService;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.OmnixApiException;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(value = {JwtConfigurationProperties.class})
public class JwtTokenManagerImpl implements JwtTokenManager {

    private final LocalParamStorage localParamStorage;
    private final OmnixEncryptionService omnixEncryptionService;
    private final JwtConfigurationProperties configurationProperties;
    private static final String DEFAULT_TOKEN_TYPE = "Bearer";
    private static final String JWT_TOKEN_ISSUER = "Tensorpoint";

    @Override
    public IdentityAuth generateToken(AppUser appUser, String emailAddress, String organizationId) {
        String channel = appUser.getChannelName();
        String algorithm = appUser.getEncryptionAlgorithm().name();
        String configuredTokenExpiry =
                localParamStorage.getParamValueOrDefault(
                        JwtTokenKey.PLATFORM_USER_TOKEN_EXPIRY_IN_MIN,
                        configurationProperties.getAdminUserTokenExpirationInMin());
        String identity = String.join(StringValues.FORWARD_STROKE, channel, emailAddress);
        String encryptedSubject =
                omnixEncryptionService.encryptWithKey(
                        algorithm, identity, configurationProperties.getSubjectEncryptionKey());
        Claims claims =
                Jwts.claims()
                        .setAudience(channel)
                        .setId(encryptedSubject)
                        .setIssuedAt(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                        .setIssuer(JWT_TOKEN_ISSUER)
                        .setSubject(organizationId);
        String accessToken =
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(encryptedSubject)
                        .setIssuer(JWT_TOKEN_ISSUER)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(
                                Date.from(
                                        Instant.now()
                                                .plus(
                                                        Long.parseLong(configuredTokenExpiry),
                                                        ChronoUnit.MINUTES)
                                                .plus(1, ChronoUnit.HOURS)))
                        .signWith(SignatureAlgorithm.HS256, configurationProperties.getJwtKey())
                        .compact();
        String tokenIssuedAt =
                claims.getIssuedAt()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                        .toString();
        String tokenExpiredAt =
                claims.getExpiration()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                        .toString();
        return IdentityAuth.builder()
                .accessToken(accessToken)
                .type(DEFAULT_TOKEN_TYPE)
                .tokenIssuedAt(tokenIssuedAt)
                .tokenExpiredAt(tokenExpiredAt)
                .build();
    }

    @Override
    public String extractPlatformUserEmail(AppUser appUser, String bearerToken) {
        try {
            String algorithm = appUser.getEncryptionAlgorithm().name();
            bearerToken = CommonUtil.cleanToken(bearerToken);
            Claims claims = getClaimsFromToken(bearerToken);
            String encryptedSubject = claims.getId();
            String decryptedIdentity =
                    omnixEncryptionService.decryptWithKey(
                            algorithm,
                            encryptedSubject,
                            configurationProperties.getSubjectEncryptionKey());
            String[] identityTokens = decryptedIdentity.split(StringValues.FORWARD_STROKE);
            return identityTokens[1];
        } catch (ExpiredJwtException exception) {
            throw OmnixApiException.newInstance()
                    .withCode(ResponseCode.INVALID_CREDENTIALS)
                    .withMessage("Expired session");
        }
    }

    @Override
    public String extractOrganizationIdFromToken(AppUser appUser, String bearerToken){
        try{
            String algorithm = appUser.getEncryptionAlgorithm().name();
            bearerToken = CommonUtil.cleanToken(bearerToken);
            Claims claims = getClaimsFromToken(bearerToken);
            return claims.getSubject();
        }catch (ExpiredJwtException exception){
            throw OmnixApiException.newInstance()
                    .withCode(ResponseCode.INVALID_CREDENTIALS)
                    .withMessage("Expired session");
        }
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(configurationProperties.getJwtKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
