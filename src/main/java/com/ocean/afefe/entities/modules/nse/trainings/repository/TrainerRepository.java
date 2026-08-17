package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.ocean.afefe.entities.modules.nse.trainings.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrainerRepository extends JpaRepository<Trainer, UUID> {
    Optional<Trainer> findByUser_Id(UUID userId);
    Optional<Trainer> findByUserAndOrg_Id(User user, UUID orgId);
}
