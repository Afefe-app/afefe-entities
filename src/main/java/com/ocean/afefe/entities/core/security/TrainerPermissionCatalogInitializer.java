package com.ocean.afefe.entities.core.security;

import com.ocean.afefe.entities.modules.auth.models.Permission;
import com.ocean.afefe.entities.modules.auth.models.TrainerPermission;
import com.ocean.afefe.entities.modules.auth.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Seeds {@link TrainerPermission} keys into the shared permissions catalog so trainer-org roles can bind them.
 * Default org roles are created lazily per organisation on first user-management access.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TrainerPermissionCatalogInitializer {

    private final PermissionRepository permissionRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void onReady() {
        Arrays.stream(TrainerPermission.values()).forEach(this::upsertPermission);
        log.info("Trainer permission catalog initialized ({} keys)", TrainerPermission.values().length);
    }

    private void upsertPermission(TrainerPermission trainerPermission) {
        permissionRepository
                .findByPermissionKey(trainerPermission.name())
                .map(existing -> {
                    existing.setDescription(trainerPermission.getDescription());
                    existing.setSystemDefined(true);
                    return permissionRepository.save(existing);
                })
                .orElseGet(() -> permissionRepository.save(Permission.builder()
                        .permissionKey(trainerPermission.name())
                        .description(trainerPermission.getDescription())
                        .systemDefined(true)
                        .build()));
    }
}
