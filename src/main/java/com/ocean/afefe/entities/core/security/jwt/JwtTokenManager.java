package com.ocean.afefe.entities.core.security.jwt;

import com.ocean.afefe.entities.core.security.data.IdentityAuth;
import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import io.jsonwebtoken.Claims;

public interface JwtTokenManager {
    IdentityAuth generateToken(AppUser appUser, String emailAddress);

    String extractPlatformUserEmail(AppUser appUser, String bearerToken);

    Claims getClaimsFromToken(String token);
}
