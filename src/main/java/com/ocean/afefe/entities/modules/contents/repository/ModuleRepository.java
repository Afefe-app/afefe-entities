package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {

    List<Module> findByCourseVersionId(UUID courseVersionId);
}
