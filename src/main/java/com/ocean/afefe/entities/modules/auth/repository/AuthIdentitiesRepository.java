package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.AuthIdentities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthIdentitiesRepository extends JpaRepository<AuthIdentities, UUID> {
}
