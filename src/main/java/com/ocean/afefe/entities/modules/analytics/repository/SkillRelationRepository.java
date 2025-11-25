package com.ocean.afefe.entities.modules.analytics.repository;

import com.ocean.afefe.entities.modules.analytics.model.SkillRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SkillRelationRepository extends JpaRepository<SkillRelation, UUID> {
}
