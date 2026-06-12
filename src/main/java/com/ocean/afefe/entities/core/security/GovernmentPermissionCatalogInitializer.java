package com.ocean.afefe.entities.core.security;

import com.ocean.afefe.entities.core.service.OrganizationService;
import com.ocean.afefe.entities.modules.auth.models.GovernmentPermission;
import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import com.ocean.afefe.entities.modules.auth.models.OrgMemberRole;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.Permission;
import com.ocean.afefe.entities.modules.auth.models.Role;
import com.ocean.afefe.entities.modules.auth.models.RolePermission;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import com.ocean.afefe.entities.modules.auth.repository.OrgMemberRepository;
import com.ocean.afefe.entities.modules.auth.repository.OrgMemberRoleRepository;
import com.ocean.afefe.entities.modules.auth.repository.PermissionRepository;
import com.ocean.afefe.entities.modules.auth.repository.RolePermissionRepository;
import com.ocean.afefe.entities.modules.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class GovernmentPermissionCatalogInitializer {

    private final OrganizationService organizationService;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final OrgMemberRepository orgMemberRepository;
    private final OrgMemberRoleRepository orgMemberRoleRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onReady() {
        Map<String, Permission> permissionsByKey = upsertPermissions();
        Organization superOrg = organizationService.getSuperOrganization();
        Role superAdminRole = upsertSystemRole(
                superOrg,
                GovernmentAdminPermissionResolver.GOVERNMENT_SUPER_ADMIN_ROLE,
                "Full government portal access",
                EnumSet.allOf(GovernmentPermission.class),
                permissionsByKey);
        upsertSystemRole(
                superOrg,
                GovernmentAdminPermissionResolver.GOVERNMENT_READ_ONLY_ROLE,
                "Read-only government portal access",
                Arrays.stream(GovernmentPermission.values())
                        .filter(GovernmentPermission::isReadPermission)
                        .collect(Collectors.toCollection(() -> EnumSet.noneOf(GovernmentPermission.class))),
                permissionsByKey);
        bootstrapAdminsWithoutRoles(superOrg, superAdminRole);
        log.info("Government permission catalog initialized");
    }

    private Map<String, Permission> upsertPermissions() {
        return Arrays.stream(GovernmentPermission.values())
                .map(this::upsertPermission)
                .collect(Collectors.toMap(Permission::getPermissionKey, Function.identity()));
    }

    private Permission upsertPermission(GovernmentPermission governmentPermission) {
        return permissionRepository
                .findByPermissionKey(governmentPermission.name())
                .map(existing -> {
                    existing.setDescription(governmentPermission.getDescription());
                    existing.setSystemDefined(true);
                    return permissionRepository.save(existing);
                })
                .orElseGet(() -> permissionRepository.save(Permission.builder()
                        .permissionKey(governmentPermission.name())
                        .description(governmentPermission.getDescription())
                        .systemDefined(true)
                        .build()));
    }

    private Role upsertSystemRole(
            Organization superOrg,
            String roleName,
            String description,
            EnumSet<GovernmentPermission> permissions,
            Map<String, Permission> permissionsByKey) {
        Role role = roleRepository
                .findByOrganizationAndName(superOrg, roleName)
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .organization(superOrg)
                        .name(roleName)
                        .description(description)
                        .systemRole(true)
                        .build()));
        rolePermissionRepository.deleteByRole(role);
        rolePermissionRepository.flush();
        for (GovernmentPermission governmentPermission : permissions) {
            Permission permission = permissionsByKey.get(governmentPermission.name());
            if (permission != null) {
                rolePermissionRepository.save(RolePermission.builder()
                        .role(role)
                        .permission(permission)
                        .build());
            }
        }
        rolePermissionRepository.flush();
        return role;
    }

    private void bootstrapAdminsWithoutRoles(Organization superOrg, Role superAdminRole) {
        List<OrgMember> admins =
                orgMemberRepository.findJoinedGovernmentAdminsByOrganization(
                                superOrg.getId(), UserType.PLATFORM_ADMIN, null, org.springframework.data.domain.Pageable.unpaged())
                        .getContent();
        for (OrgMember admin : admins) {
            if (orgMemberRoleRepository.countByOrgMember(admin) == 0) {
                orgMemberRoleRepository.save(OrgMemberRole.builder()
                        .orgMember(admin)
                        .role(superAdminRole)
                        .build());
            }
        }
    }
}
