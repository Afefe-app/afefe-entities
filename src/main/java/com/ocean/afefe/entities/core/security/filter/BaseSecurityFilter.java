package com.ocean.afefe.entities.core.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensorpoint.toolkit.tpointcore.annotation.NoAuth;
import com.tensorpoint.toolkit.tpointcore.annotation.NoAuthorization;
import com.tensorpoint.toolkit.tpointcore.commons.ApiBaseResponse;
import com.tensorpoint.toolkit.tpointcore.commons.OmnixApiException;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface BaseSecurityFilter {

    String FORBIDDEN_RESOURCE_MESSAGE = "Forbidden resource";
    String FORBIDDEN_TENANT_ACCESS_MESSAGE = "Forbidden tenant access";
    String UNKNOWN_APPLICATION = "Unknown application";
    String UNKNOWN_CHANNEL = "Unknown channel";
    String UNAUTHORIZED = "Unauthorized";
    String UNKNOWN_ORGANIZATION = "Unknown organization";
    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    default boolean noRequiresChannelAuthentication(
            RequestMappingHandlerMapping handlerMapping, HttpServletRequest request) {
        try {
            this.getMappedHandlerMethod(handlerMapping, request);
            HandlerMethod handlerMethod = this.getMappedHandlerMethod(handlerMapping, request);
            return Objects.nonNull(handlerMethod)
                    && handlerMethod.getMethod().isAnnotationPresent(NoAuth.class);
        } catch (Exception var4) {
            return false;
        }
    }

    default boolean noRequiresAuthorization(
            RequestMappingHandlerMapping handlerMapping, HttpServletRequest request) {
        try {
            HandlerMethod handlerMethod = this.getMappedHandlerMethod(handlerMapping, request);
            return Objects.nonNull(handlerMethod)
                    && handlerMethod.getMethod().isAnnotationPresent(NoAuthorization.class);
        } catch (Exception var4) {
            return false;
        }
    }

    default boolean noRequireBothChannelAndEntityAuth(
            RequestMappingHandlerMapping handlerMapping, HttpServletRequest request) {
        return this.noRequiresChannelAuthentication(handlerMapping, request)
                && this.noRequiresAuthorization(handlerMapping, request);
    }

    private HandlerMethod getMappedHandlerMethod(
            RequestMappingHandlerMapping handlerMapping, HttpServletRequest request) {
        return (HandlerMethod)
                handlerMapping.getHandlerMethods().entrySet().stream()
                        .filter(
                                (entry) -> {
                                    RequestMappingInfo requestMappingInfo = entry.getKey();
                                    String pattern =
                                            (String)
                                                    (new ArrayList(
                                                                    requestMappingInfo
                                                                            .getPatternValues()))
                                                            .get(0);
                                    boolean pathMatch =
                                            ANT_PATH_MATCHER.match(
                                                    pattern, request.getRequestURI());
                                    boolean methodMatch =
                                            requestMappingInfo
                                                    .getMethodsCondition()
                                                    .getMethods()
                                                    .contains(
                                                            RequestMethod.valueOf(
                                                                    request.getMethod()
                                                                            .toUpperCase()));
                                    return pathMatch && methodMatch;
                                })
                        .map(Map.Entry::getValue)
                        .findFirst()
                        .orElse(null);
    }

    default void pushSecurityMessage(OmnixApiException e, HttpServletResponse response) {
        String errorJson = getErrorJson(e);
        getResponseWriter(response, e.getStatusCode()).write(errorJson);
    }

    default void pushSecurityMessage(Exception e, HttpServletResponse response) {
        if (e instanceof OmnixApiException omnixApiException) {
            pushSecurityMessage(omnixApiException, response);
        }
        String errorJson = getErrorJson(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
        getResponseWriter(response, HttpStatus.INTERNAL_SERVER_ERROR.value()).write(errorJson);
    }

    @SneakyThrows
    default PrintWriter getResponseWriter(HttpServletResponse response, int statusCode) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(statusCode);
        return response.getWriter();
    }

    @SneakyThrows
    default String getErrorJson(OmnixApiException e) {
        ApiBaseResponse baseResponse = new ApiBaseResponse();
        baseResponse.setResponseCode(e.getCode());
        baseResponse.setResponseMessage(e.getMessage());
        baseResponse.setErrors(new ArrayList<>(List.of(e.getMessage())));
        return OBJECT_MAPPER.writeValueAsString(baseResponse);
    }

    @SneakyThrows
    default String getErrorJson(String responseCode, String message) {
        ApiBaseResponse baseResponse = new ApiBaseResponse();
        baseResponse.setResponseCode(responseCode);
        baseResponse.setResponseMessage(message);
        baseResponse.setErrors(new ArrayList<>(List.of(message)));
        return OBJECT_MAPPER.writeValueAsString(baseResponse);
    }
}
