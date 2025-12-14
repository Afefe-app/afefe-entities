package com.ocean.afefe.entities.common;

import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import io.grpc.Context;
import io.grpc.Metadata;

public interface GrpcConstants {

    Context.Key<AppUser> APP_USER_CONTEXT = Context.key(CommonValues.APP_USER_KEY);
    Context.Key<User> PLATFORM_USER_CONTEXT = Context.key(CommonValues.PLATFORM_USER_KEY);
    Context.Key<OrgMember> ORG_MEMBER_CONTEXT = Context.key(CommonValues.ORG_MEMBER_KEY);
    Context.Key<Organization> ORGANIZATION_CONTEXT = Context.key(CommonValues.ORGANIZATION_KEY);

    Context.Key<RequestContextMeta> REQUEST_CONTEXT_META_CONTEXT = Context.key(CommonValues.REQUEST_CONTEXT_META_KEY);
    Context.Key<TenantUser> TENANT_USER_CONTEXT = Context.key(CommonValues.TENANT_USER_KEY);

    Metadata.Key<String> AUTH_HEADER_KEY = Metadata.Key.of(StringValues.AUTH_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);
    Metadata.Key<String> CHANNEL_ID_KEY = Metadata.Key.of(StringValues.CHANNEL_ID, Metadata.ASCII_STRING_MARSHALLER);
    Metadata.Key<String> CHANNEL_SECRET_KEY = Metadata.Key.of(StringValues.CHANNEL_SECRET, Metadata.ASCII_STRING_MARSHALLER);
}
