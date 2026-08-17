package com.ocean.afefe.entities.modules.nse.auth.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TraineeRepository extends JpaRepository<Trainee, UUID> {
    Optional<Trainee> findByUser_IdAndOrganization_Id(UUID userId, UUID organizationId);

    Optional<Trainee> findByMembershipIdIgnoreCaseAndOrganization_Id(String membershipId, UUID organizationId);

    boolean existsByMembershipIdIgnoreCaseAndOrganization_Id(String membershipId, UUID organizationId);
}
