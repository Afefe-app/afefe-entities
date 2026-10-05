package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<Role> findByOrganizationOrderByNameAsc(Organization organization);

    Optional<Role> findByOrganizationAndName(Organization organization, String name);

    Optional<Role> findByOrganizationAndNameIgnoreCase(Organization organization, String name);

    Optional<Role> findByIdAndOrganization(UUID id, Organization organization);

    boolean existsByOrganizationAndName(Organization organization, String name);

    boolean existsByOrganizationAndNameIgnoreCase(Organization organization, String name);

    @Query("""
            SELECT r FROM Role r
            WHERE r.organization = :organization
              AND (:search IS NULL OR :search = ''
                   OR LOWER(r.name) LIKE LOWER(CONCAT('%', :search, '%')))
            ORDER BY r.name ASC
            """)
    Page<Role> findByOrganizationWithOptionalSearch(
            @Param("organization") Organization organization,
            @Param("search") String search,
            Pageable pageable);
}
