package com.ocean.afefe.entities.core.security;

import com.ocean.afefe.entities.core.service.OrganizationService;
import com.ocean.afefe.entities.modules.auth.models.GovernmentPermission;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import com.ocean.afefe.entities.modules.auth.repository.OrgMemberRoleRepository;
import com.ocean.afefe.entities.modules.auth.repository.RolePermissionRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GovernmentAdminPermissionResolver {

    public static final String GOVERNMENT_SUPER_ADMIN_ROLE = "GOVERNMENT_SUPER_ADMIN";
    public static final String GOVERNMENT_READ_ONLY_ROLE = "GOVERNMENT_READ_ONLY";

    private final OrganizationService organizationService;
    private final RolePermissionRepository rolePermissionRepository;
    private final OrgMemberRoleRepository orgMemberRoleRepository;

    @Transactional(readOnly = true)
    public Set<GovernmentPermission> resolveEffectivePermissions(OrgMember orgMember) {
        validateGovernmentAdmin(orgMember);
        List<String> keys = rolePermissionRepository.findPermissionKeysByOrgMemberId(orgMember.getId());
        if (keys == null || keys.isEmpty()) {
            return Set.of();
        }
        Set<GovernmentPermission> permissions = EnumSet.noneOf(GovernmentPermission.class);
        for (String key : keys) {
            try {
                permissions.add(GovernmentPermission.valueOf(key));
            } catch (IllegalArgumentException ignored) {
                // ignore unknown legacy keys
            }
        }
        return permissions;
    }

    @Transactional(readOnly = true)
    public boolean hasPermission(OrgMember orgMember, GovernmentPermission permission) {
        return resolveEffectivePermissions(orgMember).contains(permission);
    }

    @Transactional(readOnly = true)
    public boolean hasAnyPermission(OrgMember orgMember, GovernmentPermission... permissions) {
        Set<GovernmentPermission> effective = resolveEffectivePermissions(orgMember);
        for (GovernmentPermission permission : permissions) {
            if (effective.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean hasAllPermissions(OrgMember orgMember, GovernmentPermission... permissions) {
        Set<GovernmentPermission> effective = resolveEffectivePermissions(orgMember);
        for (GovernmentPermission permission : permissions) {
            if (!effective.contains(permission)) {
                return false;
            }
        }
        return true;
    }

    @Transactional(readOnly = true)
    public Set<String> resolveAssignedRoleNames(OrgMember orgMember) {
        validateGovernmentAdmin(orgMember);
        return orgMemberRoleRepository.findByOrgMember(orgMember).stream()
                .map(omr -> omr.getRole().getName())
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Transactional(readOnly = true)
    public boolean hasAnyRole(OrgMember orgMember, String... roleNames) {
        Set<String> assigned = resolveAssignedRoleNames(orgMember);
        for (String roleName : roleNames) {
            if (assigned.contains(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void validateGovernmentAdmin(OrgMember orgMember) {
        if (orgMember == null || orgMember.getUser() == null || orgMember.getOrganization() == null) {
            throw HttpUtil.getResolvedException(ResponseCode.UNAUTHORIZED, "Government admin membership is required");
        }
        if (orgMember.getJoinedAt() == null) {
            throw HttpUtil.getResolvedException(ResponseCode.UNAUTHORIZED, "Government admin membership is not active");
        }
        if (!UserType.PLATFORM_ADMIN.equals(orgMember.getUser().getUserType())) {
            throw HttpUtil.getResolvedException(ResponseCode.UNAUTHORIZED, "User is not a government admin");
        }
        Organization superOrg = organizationService.getSuperOrganization();
        if (!Objects.equals(superOrg.getId(), orgMember.getOrganization().getId())) {
            throw HttpUtil.getResolvedException(ResponseCode.UNAUTHORIZED, "Government admin must belong to the super organization");
        }
        if (!orgMember.getUser().isActive()) {
            throw HttpUtil.getResolvedException(ResponseCode.UNAUTHORIZED, "Government admin account is inactive");
        }
    }
}
