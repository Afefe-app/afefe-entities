package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.Trainee;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, UUID>, JpaSpecificationExecutor<Trainee> {

    Optional<Trainee> findByUserAndOrganization(User user, Organization organization);

    Optional<Trainee> findByIdAndOrganization(UUID id, Organization organization);

    @EntityGraph(attributePaths = "user")
    List<Trainee> findAllByOrganization(Organization organization);

    boolean existsByUser_IdAndOrganization(UUID userId, Organization organization);
}
