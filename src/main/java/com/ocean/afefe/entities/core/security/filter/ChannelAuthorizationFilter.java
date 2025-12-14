package com.ocean.afefe.entities.core.security.filter;

import com.ocean.afefe.entities.common.CommonValues;
import com.ocean.afefe.entities.common.SecurityPathsProps;
import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import com.ocean.afefe.entities.modules.appuser.repository.AppUserRepository;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ChannelAuthorizationFilter extends OncePerRequestFilter implements BaseSecurityFilter {

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    private final SecurityPathsProps securityPathsProps;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final boolean isPublicUrl = HttpUtil.matchesAny(request, securityPathsProps.exclusions());
        if (noRequiresChannelAuthentication(handlerMapping, request) || isPublicUrl) {
            filterChain.doFilter(request, response);
            return;
        }
        String channelId = request.getHeader(StringValues.CHANNEL_ID);
        String channelSecret = request.getHeader(StringValues.CHANNEL_SECRET);
        if(CommonUtil.anyNullOrEmpty(channelId, channelSecret)){
            pushSecurityMessage(HttpUtil.getResolvedException(ResponseCode.UNAUTHENTICATED, UNKNOWN_APPLICATION), response);
            return;
        }
        AppUser appUser = appUserRepository.findFirstByChannelId(channelId);
        if(Objects.isNull(appUser)){
            pushSecurityMessage(HttpUtil.getResolvedException(ResponseCode.FORBIDDEN_APP_USER, UNKNOWN_CHANNEL), response);
            return;
        }
        if(!passwordEncoder.matches(channelSecret, appUser.getChannelSecret())){
            pushSecurityMessage(HttpUtil.getResolvedException(ResponseCode.FORBIDDEN_APP_USER, UNKNOWN_CHANNEL), response);
            return;
        }
        request.setAttribute(CommonValues.APP_USER_KEY, appUser);
        filterChain.doFilter(request, response);
    }
}
