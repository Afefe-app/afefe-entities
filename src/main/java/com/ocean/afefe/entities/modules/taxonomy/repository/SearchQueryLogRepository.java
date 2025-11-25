package com.ocean.afefe.entities.modules.taxonomy.repository;

import com.ocean.afefe.entities.modules.taxonomy.models.SearchQueryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SearchQueryLogRepository extends JpaRepository<SearchQueryLog, UUID> {
}
