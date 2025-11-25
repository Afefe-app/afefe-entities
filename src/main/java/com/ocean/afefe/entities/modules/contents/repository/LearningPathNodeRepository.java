package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.LearningPathNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LearningPathNodeRepository extends JpaRepository<LearningPathNode, UUID> {
}
