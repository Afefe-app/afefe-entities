package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.CourseVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseVersionRepository extends JpaRepository<CourseVersion, UUID> {
    Optional<CourseVersion> findTopByCourseOrderByVersionNumDesc(Course course);
}
