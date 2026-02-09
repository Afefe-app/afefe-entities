package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.enrollments.models.CourseWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CourseWishListRepository extends JpaRepository<CourseWishList, UUID> {

    List<CourseWishList> findAllByUserAndOrg(User user, Organization org);

    Optional<CourseWishList> findByUserAndCourse(User user, Course course);

    boolean existsByUserAndCourse(User user, Course course);

    @Query("SELECT cw.course.id FROM CourseWishList cw WHERE cw.user = :user AND cw.course.id IN :courseIds")
    Set<UUID> findSavedCourseIdsByUserAndCourseIdIn(@Param("user") User user, @Param("courseIds") Collection<UUID> courseIds);
}
