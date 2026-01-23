package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.CourseRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRatingRepository extends JpaRepository<CourseRating, UUID>, QuerydslPredicateExecutor<CourseRating> {

    @Query("select cr from CourseRating cr where cr.course = :course and cr.user = :user")
    Optional<CourseRating> findByCourseAndUser(@Param("course") Course course, @Param("user") User user);

    @Query("select cr from CourseRating cr where cr.course = :course")
    List<CourseRating> findAllByCourse(@Param("course") Course course);

    @Query("select cr from CourseRating cr where cr.course = :course and cr.org = :org")
    List<CourseRating> findAllByCourseAndOrg(@Param("course") Course course, @Param("org") Organization org);

    @Query("select avg(cr.rating) from CourseRating cr where cr.course = :course")
    Double getAverageRatingByCourse(@Param("course") Course course);

    @Query("select count(cr) from CourseRating cr where cr.course = :course")
    Long getRatingCountByCourse(@Param("course") Course course);
}
