package com.ocean.afefe.entities.core.security.aspect;

import com.ocean.afefe.entities.common.TenantUser;
import com.ocean.afefe.entities.core.security.GovernmentAdminPermissionResolver;
import com.ocean.afefe.entities.core.security.annotation.RequiresPermission;
import com.ocean.afefe.entities.core.security.annotation.RequiresRole;
import com.ocean.afefe.entities.modules.auth.models.GovernmentPermission;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor
public class GovernmentPermissionAuthorizationAspect {

    private final GovernmentAdminPermissionResolver governmentAdminPermissionResolver;

    @Before("@annotation(com.ocean.afefe.entities.core.security.annotation.RequiresPermission)"
            + " || @annotation(com.ocean.afefe.entities.core.security.annotation.RequiresRole)")
    public void authorize(JoinPoint joinPoint) {
        TenantUser tenantUser = extractTenantUser(joinPoint.getArgs());
        OrgMember orgMember = tenantUser.getOrganizationUser();
        governmentAdminPermissionResolver.validateGovernmentAdmin(orgMember);

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
        if (requiresPermission != null) {
            assertPermission(orgMember, requiresPermission);
        }
        RequiresRole requiresRole = method.getAnnotation(RequiresRole.class);
        if (requiresRole != null) {
            assertRole(orgMember, requiresRole);
        }
    }

    private void assertPermission(OrgMember orgMember, RequiresPermission requiresPermission) {
        GovernmentPermission[] required = requiresPermission.value();
        if (required.length == 0) {
            return;
        }
        boolean allowed = requiresPermission.mode() == RequiresPermission.Mode.ALL
                ? governmentAdminPermissionResolver.hasAllPermissions(orgMember, required)
                : governmentAdminPermissionResolver.hasAnyPermission(orgMember, required);
        if (!allowed) {
            throw HttpUtil.getResolvedException(
                    ResponseCode.UNAUTHORIZED,
                    "Missing required government permission: " + Arrays.toString(required));
        }
    }

    private void assertRole(OrgMember orgMember, RequiresRole requiresRole) {
        String[] required = requiresRole.value();
        if (required.length == 0) {
            return;
        }
        Set<String> assigned = governmentAdminPermissionResolver.resolveAssignedRoleNames(orgMember);
        boolean allowed = switch (requiresRole.mode()) {
            case ALL -> Arrays.stream(required).allMatch(assigned::contains);
            case ANY -> Arrays.stream(required).anyMatch(assigned::contains);
        };
        if (!allowed) {
            throw HttpUtil.getResolvedException(
                    ResponseCode.UNAUTHORIZED, "Missing required government role: " + Arrays.toString(required));
        }
    }

    private static TenantUser extractTenantUser(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof TenantUser tenantUser) {
                return tenantUser;
            }
        }
        throw HttpUtil.getResolvedException(ResponseCode.UNAUTHENTICATED, "Tenant user context is required");
    }
}
