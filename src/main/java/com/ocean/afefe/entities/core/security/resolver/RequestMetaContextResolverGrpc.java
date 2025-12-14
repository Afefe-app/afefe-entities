package com.ocean.afefe.entities.core.security.resolver;

import com.ocean.afefe.entities.common.GrpcConstants;
import com.ocean.afefe.entities.common.RequestContextMeta;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.core.annotation.Order;

import java.util.HashMap;

@Slf4j
@Order(2)
@RequiredArgsConstructor
@GrpcGlobalServerInterceptor
public class RequestMetaContextResolverGrpc implements ServerInterceptor {


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();
        RequestContextMeta requestContextMeta = RequestContextMeta.builder()
                .requestUrl(fullMethodName)
                .requestParams(new HashMap<>())
                .requestHeaders(new HashMap<>())
                .build();
        Context currentContext = Context.current().withValue(GrpcConstants.REQUEST_CONTEXT_META_CONTEXT, requestContextMeta);
        return Contexts.interceptCall(currentContext, serverCall, metadata, serverCallHandler);
    }
}
