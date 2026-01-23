package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>, QuerydslPredicateExecutor<Course> {

    boolean existsByTitleHashAndOwnerInstructorAndOrg(String title, Instructor instructor, Organization organization);
}
