package com.ocean.afefe.entities.modules.analytics.repository;

import com.ocean.afefe.entities.modules.analytics.model.UserSkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSkillLevelRepository extends JpaRepository<UserSkillLevel, UUID> {
}
