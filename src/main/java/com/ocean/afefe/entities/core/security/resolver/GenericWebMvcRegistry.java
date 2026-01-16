package com.ocean.afefe.entities.core.security.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenericWebMvcRegistry implements WebMvcConfigurer {

    private final TenantUserArgumentResolver tenantUserArgumentResolver;
    private final RequestContextMetaArgumentResolver requestContextMetaArgumentResolver;

    public void addInterceptors(InterceptorRegistry registry) {
    }

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requestContextMetaArgumentResolver);
        resolvers.add(tenantUserArgumentResolver);
    }
}
