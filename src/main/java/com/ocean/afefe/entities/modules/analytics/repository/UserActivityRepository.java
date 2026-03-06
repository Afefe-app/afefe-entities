package com.ocean.afefe.entities.modules.analytics.repository;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.analytics.model.UserActivity;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, BaseUUIDEntity> {

    @Query("SELECT ua FROM UserActivity ua " +
           "JOIN FETCH ua.user " +
           "JOIN FETCH ua.course " +
           "WHERE ua.course.ownerInstructor = :instructor " +
           "ORDER BY ua.createdAt DESC")
    Page<UserActivity> findByCourseOwnerInstructorOrderByCreatedAtDesc(
            @Param("instructor") Instructor instructor,
            Pageable pageable);

    @Query("SELECT DISTINCT ua.course FROM UserActivity ua " +
           "WHERE ua.course.ownerInstructor = :instructor " +
           "AND ua.course.status = :status " +
           "ORDER BY ua.course.updatedAt DESC")
    Page<Course> findDistinctCoursesByOwnerInstructorAndStatusOrderByUpdatedAtDesc(
            @Param("instructor") Instructor instructor,
            @Param("status") com.ocean.afefe.entities.modules.contents.models.CourseStatus status,
            Pageable pageable);

    @Query("SELECT DISTINCT ua.course FROM UserActivity ua " +
           "WHERE ua.course.ownerInstructor = :instructor " +
           "ORDER BY ua.course.updatedAt DESC")
    Page<Course> findDistinctCoursesByOwnerInstructorOrderByUpdatedAtDesc(
            @Param("instructor") Instructor instructor,
            Pageable pageable);

    @Query("SELECT COUNT(DISTINCT ua.user.id) FROM UserActivity ua WHERE ua.course.id = :courseId AND ua.createdAt >= :after")
    long countDistinctActiveLearnersByCourseIdAndCreatedAtAfter(@Param("courseId") UUID courseId, @Param("after") Instant after);
}
