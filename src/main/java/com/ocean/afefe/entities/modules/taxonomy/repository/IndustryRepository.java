package com.ocean.afefe.entities.modules.taxonomy.repository;

import com.ocean.afefe.entities.modules.taxonomy.models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, UUID> {

    @Query("select i from Industry i where i.id in :ids")
    List<Industry> findAllByIdIn(@Param("ids") List<UUID> ids);
}
