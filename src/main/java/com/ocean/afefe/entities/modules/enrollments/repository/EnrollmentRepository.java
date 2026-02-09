package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID>, QuerydslPredicateExecutor<Enrollment> {

    Optional<Enrollment> findByUserAndCourseAndStatus(User user, Course course, EnrollmentStatus status);

    Optional<Enrollment> findByUserAndCourse(User user, Course course);

    long countByCourse(Course course);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.ownerInstructor = :instructor")
    long countDistinctStudentsByInstructor(@Param("instructor") Instructor instructor);
}
