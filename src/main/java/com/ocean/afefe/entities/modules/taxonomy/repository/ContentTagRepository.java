package com.ocean.afefe.entities.modules.taxonomy.repository;

import com.ocean.afefe.entities.modules.taxonomy.models.ContentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContentTagRepository extends JpaRepository<ContentTag, UUID> {
}
