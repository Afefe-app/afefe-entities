package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    List<Role> findByOrganizationOrderByNameAsc(Organization organization);

    Optional<Role> findByOrganizationAndName(Organization organization, String name);

    boolean existsByOrganizationAndName(Organization organization, String name);
}
