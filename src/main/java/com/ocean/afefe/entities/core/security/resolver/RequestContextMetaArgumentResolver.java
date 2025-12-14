package com.ocean.afefe.entities.core.security.resolver;

import com.ocean.afefe.entities.common.RequestContextMeta;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class RequestContextMetaArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType().isAssignableFrom(RequestContextMeta.class);
    }

    @Override
    @NonNull
    public Object resolveArgument(@NonNull MethodParameter parameter, @NonNull ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @NonNull WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        return RequestContextMeta.builder()
                .requestUrl(servletRequest.getRequestURI())
                .requestParams(new HashMap<>())
                .requestHeaders(new HashMap<>())
                .build();
    }
}
