package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.CourseRating;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRatingRepository extends JpaRepository<CourseRating, UUID>, QuerydslPredicateExecutor<CourseRating> {

    @Query("select cr from CourseRating cr where cr.course = :course and cr.user = :user")
    Optional<CourseRating> findByCourseAndUser(@Param("course") Course course, @Param("user") User user);

    @Query("select cr from CourseRating cr where cr.course = :course")
    List<CourseRating> findAllByCourse(@Param("course") Course course);

    @Query("select cr from CourseRating cr join fetch cr.user where cr.course = :course and cr.org = :org")
    List<CourseRating> findAllByCourseAndOrg(@Param("course") Course course, @Param("org") Organization org);

    @Query("select avg(cr.rating) from CourseRating cr where cr.course = :course")
    Double getAverageRatingByCourse(@Param("course") Course course);

    @Query("select count(cr) from CourseRating cr where cr.course = :course")
    Long getRatingCountByCourse(@Param("course") Course course);

    @Query("SELECT AVG(cr.rating) FROM CourseRating cr WHERE cr.course.ownerInstructor = :instructor")
    Double getAverageRatingByInstructor(@Param("instructor") Instructor instructor);

    @Query("SELECT COUNT(cr) FROM CourseRating cr WHERE cr.course.ownerInstructor = :instructor")
    long getRatingCountByInstructor(@Param("instructor") Instructor instructor);

    @Query("SELECT COUNT(cr) FROM CourseRating cr WHERE cr.course = :course AND cr.rating = :rating AND cr.org = :org")
    Long getRatingCountByCourseAndRatingAndOrg(@Param("course") Course course, @Param("rating") Integer rating, @Param("org") Organization org);

    @Query("SELECT AVG(cr.rating) FROM CourseRating cr WHERE cr.course.ownerInstructor = :instructor AND cr.ratedAt >= :after")
    Double getAverageRatingByInstructorAndRatedAtAfter(@Param("instructor") Instructor instructor, @Param("after") Instant after);

    @Query("SELECT COUNT(cr) FROM CourseRating cr WHERE cr.course.ownerInstructor = :instructor AND cr.ratedAt >= :after")
    long getRatingCountByInstructorAndRatedAtAfter(@Param("instructor") Instructor instructor, @Param("after") Instant after);

    @Query("SELECT cr FROM CourseRating cr " +
           "JOIN FETCH cr.user " +
           "JOIN FETCH cr.course " +
           "WHERE cr.course.id = :courseId " +
           "AND cr.course.ownerInstructor = :instructor " +
           "AND cr.org = :org")
    Page<CourseRating> findByCourseIdAndOwnerInstructorAndOrg(
            @Param("courseId") UUID courseId,
            @Param("instructor") Instructor instructor,
            @Param("org") Organization org,
            Pageable pageable);

    @Query("SELECT cr FROM CourseRating cr " +
           "JOIN FETCH cr.user " +
           "JOIN FETCH cr.course " +
           "WHERE cr.course.ownerInstructor = :instructor " +
           "AND cr.org = :org " +
           "AND (:courseId IS NULL OR cr.course.id = :courseId)")
    Page<CourseRating> findByOwnerInstructorAndOrg(
            @Param("instructor") Instructor instructor,
            @Param("org") Organization org,
            @Param("courseId") UUID courseId,
            Pageable pageable);

    @Query("SELECT AVG(cr.rating) FROM CourseRating cr WHERE cr.course.id = :courseId AND cr.ratedAt >= :after")
    Double getAverageRatingByCourseIdAndRatedAtAfter(@Param("courseId") UUID courseId, @Param("after") Instant after);

    @Query("SELECT AVG(cr.rating) FROM CourseRating cr WHERE cr.course.id = :courseId AND cr.ratedAt <= :date")
    Double getAverageRatingByCourseIdAndRatedAtToDate(@Param("courseId") UUID courseId, @Param("date") Instant date);

    @Query("SELECT AVG(cr.rating) FROM CourseRating cr WHERE cr.course.ownerInstructor = :instructor AND cr.ratedAt <= :date")
    Double getAverageRatingByInstructorAndRatedAtToDate(@Param("instructor") Instructor instructor, @Param("date") Instant date);

    @Query("SELECT COUNT(cr) FROM CourseRating cr WHERE cr.course.ownerInstructor = :instructor AND cr.ratedAt <= :date")
    long getRatingCountByInstructorAndRatedAtToDate(@Param("instructor") Instructor instructor, @Param("date") Instant date);

    @Query("SELECT AVG(cr.rating) FROM CourseRating cr WHERE cr.org = :org AND cr.ratedAt <= :date")
    Double getAverageRatingByOrgAndRatedAtToDate(@Param("org") Organization org, @Param("date") Instant date);
}
