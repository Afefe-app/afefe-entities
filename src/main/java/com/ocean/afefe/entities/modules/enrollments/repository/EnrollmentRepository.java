package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.Organization;
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
import java.util.Collection;
import java.util.List;
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

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.id = :courseId AND e.startedAt <= :date")
    long countDistinctLearnersByCourseIdAndStartedAtToDate(@Param("courseId") UUID courseId, @Param("date") Instant date);

    @Query("""
        SELECT COUNT(DISTINCT e.user.id)
        FROM Enrollment e
        WHERE e.course.id = :courseId
          AND e.status = com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus.COMPLETED
    """)
    long countDistinctCompletedLearnersByCourseId(@Param("courseId") UUID courseId);

    @Query("""
        SELECT DISTINCT e.course.id
        FROM Enrollment e
        WHERE e.course.org = :org
          AND e.course.status = com.ocean.afefe.entities.modules.contents.models.CourseStatus.PUBLISHED
          AND e.user.id IN :userIds
    """)
    List<UUID> findDistinctPublishedCourseIdsByOrgAndUserIds(
            @Param("org") Organization org,
            @Param("userIds") Collection<UUID> userIds
    );

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.ownerInstructor = :instructor AND e.startedAt <= :date")
    long countDistinctLearnersByInstructorAndStartedAtToDate(@Param("instructor") Instructor instructor, @Param("date") Instant date);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.org = :org AND e.startedAt <= :date")
    long countDistinctLearnersByOrgAndStartedAtToDate(@Param("org") Organization org, @Param("date") Instant date);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.org = :org AND e.startedAt >= :after")
    long countDistinctLearnersByOrgAndStartedAtAfter(@Param("org") Organization org, @Param("after") Instant after);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM Enrollment e WHERE e.course.id IN :courseIds AND e.startedAt <= :date")
    long countDistinctLearnersByCourseIdsAndStartedAtToDate(
            @Param("courseIds") Collection<UUID> courseIds,
            @Param("date") Instant date);

    @Query("""
        SELECT COUNT(DISTINCT e.user.id)
        FROM Enrollment e
        WHERE e.course.ownerInstructor = :instructor
          AND e.status = com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus.COMPLETED
          AND e.completedAt >= :startDate
          AND e.completedAt < :endDate
    """)
    long countDistinctCompletedLearnersByInstructorAndCompletedAtBetween(
            @Param("instructor") Instructor instructor,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );

    @Query("""
        SELECT COUNT(DISTINCT e.user.id)
        FROM Enrollment e
        WHERE e.course.org = :org
          AND e.status = com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus.COMPLETED
          AND e.completedAt >= :startDate
          AND e.completedAt < :endDate
    """)
    long countDistinctCompletedLearnersByOrgAndCompletedAtBetween(
            @Param("org") Organization org,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );

    @Query("""
        SELECT e.currentModule.id, COUNT(DISTINCT e.user.id)
        FROM Enrollment e
        WHERE e.course.id = :courseId
          AND e.status != 'COMPLETED'
          AND e.startedAt >= :startDate
          AND e.startedAt <= :endDate
        GROUP BY e.currentModule.id
    """)
    List<Object[]> countDropOffLearnersByModuleAndCourseIdAndStartedAtBetween(
            @Param("courseId") UUID courseId,
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate
    );

    boolean existsByUserAndCourseAndStatusIn(User user, Course course, List<EnrollmentStatus> status);
}
