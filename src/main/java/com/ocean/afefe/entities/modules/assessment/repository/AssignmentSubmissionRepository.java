package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, UUID> {

    List<AssignmentSubmission> findByAssignment_IdOrderBySubmittedAtDesc(UUID assignmentId);

    long countByAssignment_Id(UUID assignmentId);

    List<AssignmentSubmission> findByUser_IdAndAssignment_Course_IdOrderBySubmittedAtDesc(UUID userId, UUID courseId);

    List<AssignmentSubmission> findByEnrollment_IdOrderBySubmittedAtDesc(UUID enrollmentId);
}
