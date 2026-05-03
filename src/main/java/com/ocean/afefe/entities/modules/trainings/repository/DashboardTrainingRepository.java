package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.trainings.models.Trainer;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface DashboardTrainingRepository extends JpaRepository<Training, UUID> {

    long countByTrainerAndOrg(Trainer trainer, Organization org);

    long countByTrainerAndOrgAndCreatedAtBetween(Trainer trainer, Organization org, Instant start, Instant end);
}
