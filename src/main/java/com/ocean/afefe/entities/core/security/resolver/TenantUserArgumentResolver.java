package com.ocean.afefe.entities.core.security.resolver;

import com.ocean.afefe.entities.common.CommonValues;
import com.ocean.afefe.entities.common.TenantUser;
import com.ocean.afefe.entities.core.security.filter.BaseSecurityFilter;
import com.ocean.afefe.entities.core.security.jwt.JwtTokenManager;
import com.ocean.afefe.entities.core.service.UserService;
import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.repository.OrgMemberRepository;
import com.ocean.afefe.entities.modules.auth.repository.OrgRepository;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Objects;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class TenantUserArgumentResolver implements HandlerMethodArgumentResolver, BaseSecurityFilter {


    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;
    private final OrgMemberRepository orgMemberRepository;
    private final OrgRepository orgRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType().isAssignableFrom(TenantUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        AppUser appUser = (AppUser) servletRequest.getAttribute(CommonValues.APP_USER_KEY);
        if(noRequiresAuthorization(handlerMapping, servletRequest)){
            return TenantUser.builder().appUser(appUser).build();
        }
        String bearerTokenHeader = servletRequest.getHeader(StringValues.AUTH_HEADER_KEY);
        if(CommonUtil.isNullOrEmpty(bearerTokenHeader)){
            throw HttpUtil.getResolvedException(ResponseCode.UNAUTHENTICATED, UNAUTHORIZED);
        }
        String emailAddress = jwtTokenManager.extractPlatformUserEmail(appUser, bearerTokenHeader);
        String organizationId = jwtTokenManager.extractOrganizationIdFromToken(appUser, bearerTokenHeader);
        Organization organization = orgRepository.findById(UUID.fromString(organizationId)).orElse(null);
        if(Objects.isNull(organization)){
            throw HttpUtil.getResolvedException(ResponseCode.FAILED_MODEL, UNKNOWN_ORGANIZATION);
        }
        OrgMember orgMember =  orgMemberRepository.findFirstByUser_EmailAddressAndOrganization(emailAddress, organization);
        TenantUser tenantUser = TenantUser.builder()
                .user(orgMember.getUser())
                .appUser(appUser)
                .organizationUser(orgMember)
                .organization(orgMember.getOrganization())
                .build();
        servletRequest.setAttribute(CommonValues.PLATFORM_USER_KEY, orgMember.getUser());
        servletRequest.setAttribute(CommonValues.ORG_MEMBER_KEY, orgMember);
        servletRequest.setAttribute(CommonValues.ORGANIZATION_KEY, organization);
        servletRequest.setAttribute(CommonValues.TENANT_USER_KEY, tenantUser);
        return tenantUser;
    }
}
