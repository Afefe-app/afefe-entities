package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, UUID> {

    Optional<Trainer> findByUserAndOrg_Id(User user, UUID orgId);

    Optional<Trainer> findByUser_IdAndOrg_Id(UUID userId, UUID orgId);

    long countByOrg_Id(UUID orgId);

    java.util.List<Trainer> findByOrg_IdOrderByDisplayNameAsc(UUID orgId);
}
