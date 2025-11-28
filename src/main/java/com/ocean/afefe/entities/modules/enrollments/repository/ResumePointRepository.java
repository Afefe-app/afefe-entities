package com.ocean.afefe.entities.modules.enrollments.repository;
import com.ocean.afefe.entities.modules.enrollments.models.ResumePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResumePointRepository extends JpaRepository<ResumePoint, UUID> {
}
