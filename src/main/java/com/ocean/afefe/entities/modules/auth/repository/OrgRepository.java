package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.OrganizationRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface OrgRepository extends JpaRepository<Organization, UUID>, JpaSpecificationExecutor<Organization> {

    Organization findFirstByRole(OrganizationRole role);

    Optional<Organization> findFirstByGuidIgnoreCaseAndRole(String guid, OrganizationRole role);
    long countByRole(OrganizationRole role);
    boolean existsBySlugIgnoreCase(String slug);

    long countByIdNotAndCreatedAtBetween(UUID excludedId, Instant start, Instant end);
}
