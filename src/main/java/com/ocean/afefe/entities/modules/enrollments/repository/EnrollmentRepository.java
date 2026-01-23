package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID>, QuerydslPredicateExecutor<Enrollment> {
}
