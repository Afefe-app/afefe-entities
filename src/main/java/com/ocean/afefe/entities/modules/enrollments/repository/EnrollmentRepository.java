package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID>, QuerydslPredicateExecutor<Enrollment> {

    Optional<Enrollment> findByUserAndCourseAndStatus(User user, Course course, EnrollmentStatus status);

    Optional<Enrollment> findByUserAndCourse(User user, Course course);

    long countByCourse(Course course);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.ownerInstructor = :instructor")
    long countDistinctStudentsByInstructor(@Param("instructor") Instructor instructor);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.ownerInstructor = :instructor AND e.startedAt >= :after")
    long countDistinctLearnersByInstructorAndStartedAtAfter(@Param("instructor") Instructor instructor, @Param("after") Instant after);

    Page<Enrollment> findByCourse_OwnerInstructor(Instructor instructor, Pageable pageable);

    long countByCourse_OwnerInstructorAndStatus(Instructor instructor, EnrollmentStatus status);

    @Query("SELECT e FROM Enrollment e " +
           "JOIN FETCH e.user " +
           "JOIN FETCH e.course " +
           "WHERE e.course.id = :courseId " +
           "AND e.course.ownerInstructor = :instructor " +
           "ORDER BY e.startedAt DESC")
    Page<Enrollment> findByCourseIdAndOwnerInstructorOrderByStartedAtDesc(
            @Param("courseId") UUID courseId,
            @Param("instructor") Instructor instructor,
            Pageable pageable);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.id = :courseId AND e.startedAt >= :after")
    long countDistinctLearnersByCourseIdAndStartedAtAfter(@Param("courseId") UUID courseId, @Param("after") Instant after);
}
