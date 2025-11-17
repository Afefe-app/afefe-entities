package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.AuthIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthIdentitiesRepository extends JpaRepository<AuthIdentity, UUID> {
}
