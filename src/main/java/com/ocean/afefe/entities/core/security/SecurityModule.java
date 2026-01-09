package com.ocean.afefe.entities.core.security;

import com.ocean.afefe.entities.core.security.filter.ChannelAuthorizationFilter;
import com.ocean.afefe.entities.core.security.filter.ChannelAuthorizationFilterGrpc;
import com.ocean.afefe.entities.core.security.jwt.JwtConfigurationProperties;
import com.ocean.afefe.entities.core.security.jwt.JwtTokenManagerImpl;
import com.ocean.afefe.entities.core.security.otp.OtpValidationServiceImpl;
import com.ocean.afefe.entities.core.security.resolver.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        OtpValidationServiceImpl.class,
        ChannelAuthorizationFilter.class,
        ChannelAuthorizationFilterGrpc.class,
        JwtTokenManagerImpl.class,
        RequestContextMetaArgumentResolver.class,
        RequestMetaContextResolverGrpc.class,
        TenantUserArgumentResolver.class,
        TenantUserContextResolverGrpc.class,
        GenericWebMvcRegistry.class

})
@Configuration
@EnableConfigurationProperties({
        JwtConfigurationProperties.class,
})
public class SecurityModule {
}
