package com.ocean.afefe.entities.modules.analytics.repository;

import com.ocean.afefe.entities.modules.analytics.model.CareerPathRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CareerPathRequirementRepository extends JpaRepository<CareerPathRequirement, UUID> {
}
