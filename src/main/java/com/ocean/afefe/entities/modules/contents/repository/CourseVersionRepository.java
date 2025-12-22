package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.CourseVersion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseVersionRepository extends JpaRepository<CourseVersion, UUID> {
    @Query("SELECT cv FROM CourseVersion cv WHERE cv.course = :course ORDER BY cv.versionNum DESC")
    List<CourseVersion> findByCourseOrderByVersionNumDesc(@Param("course") Course course, Pageable pageable);
    
    default Optional<CourseVersion> findLatestByCourse(Course course) {
        List<CourseVersion> versions = findByCourseOrderByVersionNumDesc(course, Pageable.ofSize(1));
        return versions.isEmpty() ? Optional.empty() : Optional.of(versions.get(0));
    }

    Optional<CourseVersion> findTopByCourseOrderByVersionNumDesc(Course course);
}
