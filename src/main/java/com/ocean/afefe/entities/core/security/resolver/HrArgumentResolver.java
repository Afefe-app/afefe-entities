package com.ocean.afefe.entities.core.security.resolver;

import com.ocean.afefe.entities.common.CommonValues;
import com.ocean.afefe.entities.common.TenantUser;
import com.ocean.afefe.entities.core.security.filter.BaseSecurityFilter;
import com.ocean.afefe.entities.modules.auth.models.Hr;
import com.ocean.afefe.entities.modules.auth.repository.HrRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Resolves {@link Hr} using {@link TenantUser} on the request (from {@link TenantUserArgumentResolver}).
 * Declare {@code TenantUser} before {@code Hr} in controller method parameters.
 */
@Configuration
@RequiredArgsConstructor
public class HrArgumentResolver implements HandlerMethodArgumentResolver {

    private final HrRepository hrRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Hr.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Object attr = servletRequest.getAttribute(CommonValues.TENANT_USER_KEY);
        if (!(attr instanceof TenantUser tenantUser)) {
            throw HttpUtil.getResolvedException(
                    ResponseCode.FAILED_MODEL,
                    "Organization tenant context is required before HR resolution; declare TenantUser before Hr."
            );
        }
        if (tenantUser.getUser() == null || tenantUser.getOrganization() == null) {
            throw HttpUtil.getResolvedException(ResponseCode.FAILED_MODEL, "HR operations require a signed-in organization context.");
        }

        Hr hr = hrRepository
                .findByUserAndOrg(tenantUser.getUser(), tenantUser.getOrganization())
                .orElseThrow(() -> HttpUtil.getResolvedException(ResponseCode.FAILED_MODEL, BaseSecurityFilter.FORBIDDEN_RESOURCE_MESSAGE));
        servletRequest.setAttribute(CommonValues.HR_KEY, hr);
        return hr;
    }
}
