package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.LearningPath;
import com.ocean.afefe.entities.modules.enrollments.models.LearningPathEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningPathEnrollmentRepository extends JpaRepository<LearningPathEnrollment, UUID> {

    Optional<LearningPathEnrollment> findByUserAndOrganization(User user, Organization organization);

    boolean existsByUserAndOrganizationAndLearningPath(User user, Organization organization, LearningPath learningPath);

    @Query("""
            SELECT lpe.learningPath.id, COUNT(lpe)
            FROM LearningPathEnrollment lpe
            WHERE lpe.organization = :org AND lpe.learningPath.id IN :pathIds
            GROUP BY lpe.learningPath.id
            """)
    List<Object[]> countGroupedByLearningPathIds(
            @Param("pathIds") Collection<UUID> pathIds,
            @Param("org") Organization org);
}
