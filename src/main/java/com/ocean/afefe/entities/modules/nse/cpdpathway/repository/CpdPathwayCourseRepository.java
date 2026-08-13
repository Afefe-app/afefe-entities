package com.ocean.afefe.entities.modules.nse.cpdpathway.repository;

import com.ocean.afefe.entities.modules.nse.cpdpathway.models.CpdPathway;
import com.ocean.afefe.entities.modules.nse.cpdpathway.models.CpdPathwayCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CpdPathwayCourseRepository extends JpaRepository<CpdPathwayCourse, UUID> {
    List<CpdPathwayCourse> findByPathwayOrderByPositionAsc(CpdPathway pathway);
}
