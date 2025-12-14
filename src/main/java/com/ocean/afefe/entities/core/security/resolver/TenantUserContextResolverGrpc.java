package com.ocean.afefe.entities.core.security.resolver;

import com.ocean.afefe.entities.common.TenantUser;
import com.ocean.afefe.entities.core.security.filter.BaseSecurityFilter;
import com.ocean.afefe.entities.core.security.jwt.JwtTokenManager;
import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.repository.OrgMemberRepository;
import com.ocean.afefe.entities.modules.auth.repository.OrgRepository;
import com.tensorpoint.toolkit.bean.GrpcSecurityMetadataRegistry;
import com.tensorpoint.toolkit.instrumentation.MethodSecurityMetadata;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.core.annotation.Order;

import java.util.Objects;
import java.util.UUID;

import static com.ocean.afefe.entities.common.GrpcConstants.*;

@Slf4j
@Order(3)
@RequiredArgsConstructor
@GrpcGlobalServerInterceptor
public class TenantUserContextResolverGrpc implements ServerInterceptor, BaseSecurityFilter {

    private final OrgRepository orgRepository;
    private final JwtTokenManager jwtTokenManager;
    private final OrgMemberRepository orgMemberRepository;
    private final GrpcSecurityMetadataRegistry securityMetadataRegistry;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        try{
            String fullMethodName = serverCall.getMethodDescriptor().getFullMethodName();
            String bareMethodName = serverCall.getMethodDescriptor().getBareMethodName();
            MethodSecurityMetadata securityMetadata = securityMetadataRegistry.getMetadata(fullMethodName, bareMethodName);
            AppUser appUser = APP_USER_CONTEXT.get();
            if(securityMetadata.isNoAuthorization()){
                TenantUser tenantUser = TenantUser.builder().appUser(appUser).build();
                Context context = Context.current().withValue(TENANT_USER_CONTEXT, tenantUser);
                return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
            }

            String bearerToken = metadata.get(AUTH_HEADER_KEY);
            if(CommonUtil.isNullOrEmpty(bearerToken)){
                serverCall.close(Status.UNAUTHENTICATED.withDescription(UNAUTHORIZED), metadata);
                return new ServerCall.Listener<ReqT>() {};
            }
            String organizationId = jwtTokenManager.extractOrganizationIdFromToken(appUser, bearerToken);
            Organization organization = orgRepository.findById(UUID.fromString(organizationId)).orElse(null);
            if(Objects.isNull(organization)){
                serverCall.close(Status.INVALID_ARGUMENT.withDescription(UNKNOWN_ORGANIZATION), metadata);
                return new ServerCall.Listener<ReqT>() {};
            }
            String emailAddress = jwtTokenManager.extractPlatformUserEmail(appUser, bearerToken);
            OrgMember orgMember =  orgMemberRepository.findFirstByUser_EmailAddressAndOrganization(emailAddress, organization);
            TenantUser tenantUser = TenantUser.builder()
                    .user(orgMember.getUser())
                    .appUser(appUser)
                    .organizationUser(orgMember)
                    .organization(orgMember.getOrganization())
                    .build();
            Context context = Context.current()
                    .withValue(PLATFORM_USER_CONTEXT, orgMember.getUser())
                    .withValue(ORG_MEMBER_CONTEXT, orgMember)
                    .withValue(ORGANIZATION_CONTEXT, organization)
                    .withValue(TENANT_USER_CONTEXT, tenantUser);
            return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
        }catch (Exception e){
            serverCall.close(Status.INTERNAL.withDescription(e.getMessage()), metadata);
            return new ServerCall.Listener<ReqT>() {};
        }
    }
}
