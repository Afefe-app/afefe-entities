package com.ocean.afefe.entities.modules.nse.auth.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findBySlug(String slug);
}
