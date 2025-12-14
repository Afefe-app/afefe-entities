package com.ocean.afefe.entities.core.security.filter;

import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import com.ocean.afefe.entities.modules.appuser.repository.AppUserRepository;
import com.tensorpoint.toolkit.bean.GrpcSecurityMetadataRegistry;
import com.tensorpoint.toolkit.instrumentation.MethodSecurityMetadata;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static com.ocean.afefe.entities.common.GrpcConstants.*;

@Slf4j
@Order(1)
@RequiredArgsConstructor
@GrpcGlobalServerInterceptor
public class ChannelAuthorizationFilterGrpc implements ServerInterceptor, BaseSecurityFilter {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final GrpcSecurityMetadataRegistry securityMetadataRegistry;


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        try {
            String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();
            String bareMethodName = serverCall.getMethodDescriptor().getBareMethodName();
            MethodSecurityMetadata securityMetadata = securityMetadataRegistry.getMetadata(fullMethodName, bareMethodName);
            String channelId = metadata.get(CHANNEL_ID_KEY);
            String channelSecret = metadata.get(CHANNEL_SECRET_KEY);
            if(securityMetadata.isNoAuth()){
                return serverCallHandler.startCall(serverCall, metadata);
            }
            if (CommonUtil.anyNullOrEmpty(channelId, channelSecret)) {
                serverCall.close(Status.UNAUTHENTICATED.withDescription(UNKNOWN_APPLICATION), metadata);
                return new ServerCall.Listener<ReqT>() {
                };
            }
            AppUser appUser = appUserRepository.findFirstByChannelId(channelId);
            if (Objects.isNull(appUser)) {
                serverCall.close(Status.UNAUTHENTICATED.withDescription(UNKNOWN_CHANNEL), metadata);
                return new ServerCall.Listener<ReqT>() {
                };
            }
            if (!passwordEncoder.matches(channelSecret, appUser.getChannelSecret())) {
                serverCall.close(Status.UNAUTHENTICATED.withDescription(UNKNOWN_CHANNEL), metadata);
            }
            Context currentContext = Context.current().withValue(APP_USER_CONTEXT, appUser);
            return Contexts.interceptCall(currentContext, serverCall, metadata, serverCallHandler);
        }catch (Exception exception){
            exception.printStackTrace();
            serverCall.close(Status.UNAUTHENTICATED.withDescription(exception.getMessage()), metadata);
            return new ServerCall.Listener<ReqT>() {};
        }
    }
}
