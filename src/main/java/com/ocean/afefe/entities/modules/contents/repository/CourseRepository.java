package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.CourseStatus;
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
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID>, QuerydslPredicateExecutor<Course> {

    Page<Course> findAllByOwnerInstructorIdAndOrgId(UUID ownerInstructorId, UUID orgId, Pageable pageable);

    boolean existsByTitleHashAndOwnerInstructorAndOrg(String title, Instructor instructor, Organization organization);

    long countByOwnerInstructor(Instructor instructor);

    long countByOwnerInstructorAndCreatedAtAfter(Instructor instructor, Instant after);

    long countByOrg(Organization organization);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.org = :org AND c.createdAt >= :after")
    long countByOrgAndCreatedAtAfter(@Param("org") Organization organization, @Param("after") Instant after);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.ownerInstructor = :instructor AND c.status = :status AND c.createdAt >= :after")
    long countByOwnerInstructorAndStatusAndCreatedAtAfter(
            @Param("instructor") Instructor instructor,
            @Param("status") CourseStatus status,
            @Param("after") Instant after);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.ownerInstructor = :instructor AND c.createdAt <= :date")
    long countByOwnerInstructorAndCreatedAtToDate(@Param("instructor") Instructor instructor, @Param("date") Instant date);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.ownerInstructor = :instructor AND c.status = :status AND c.createdAt <= :date")
    long countByOwnerInstructorAndStatusAndCreatedAtToDate(
            @Param("instructor") Instructor instructor,
            @Param("status") CourseStatus status,
            @Param("date") Instant date);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.org = :org AND c.createdAt <= :date")
    long countByOrgAndCreatedAtToDate(@Param("org") Organization org, @Param("date") Instant date);

    @Query("""
        SELECT c
        FROM Course c
        WHERE c.org = :org
          AND c.status = com.ocean.afefe.entities.modules.contents.models.CourseStatus.PUBLISHED
    """)
    Page<Course> findPublishedByOrg(@Param("org") Organization org, Pageable pageable);

    @Query("""
        SELECT c
        FROM Course c
        WHERE c.org = :org
          AND c.status = com.ocean.afefe.entities.modules.contents.models.CourseStatus.PUBLISHED
          AND c.id IN :courseIds
    """)
    Page<Course> findPublishedByOrgAndIdIn(
            @Param("org") Organization org,
            @Param("courseIds") List<UUID> courseIds,
            Pageable pageable
    );

    @Query("""
        SELECT DISTINCT c.id
        FROM Course c
        WHERE c.org = :org
          AND c.status = com.ocean.afefe.entities.modules.contents.models.CourseStatus.PUBLISHED
          AND (
              LOWER(COALESCE(c.title, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(COALESCE(c.summary, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(COALESCE(c.tags, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          )
    """)
    List<UUID> findDistinctPublishedIdsByOrgAndSearch(
            @Param("org") Organization org,
            @Param("search") String search
    );
}
