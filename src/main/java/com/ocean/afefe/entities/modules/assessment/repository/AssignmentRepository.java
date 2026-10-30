package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {

    List<Assignment> findByCourse_IdAndOrg_IdOrderByDueAtDescCreatedAtDesc(UUID courseId, UUID orgId);

    Optional<Assignment> findByIdAndCourse_IdAndOrg_Id(UUID id, UUID courseId, UUID orgId);

    long countByCourse_IdAndOrg_Id(UUID courseId, UUID orgId);
}
