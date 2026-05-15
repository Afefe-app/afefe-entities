package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingEnrollmentRepository extends JpaRepository<TrainingEnrollment, UUID> {

    Optional<TrainingEnrollment> findByUserAndTraining(User user, Training training);

    Optional<TrainingEnrollment> findByUserAndTraining_Id(User user, UUID trainingId);

    List<TrainingEnrollment> findByUserAndOrgOrderByUpdatedAtDesc(User user, Organization org);

    List<TrainingEnrollment> findByUserAndOrgAndStatusInOrderByUpdatedAtDesc(
            User user, Organization org, Collection<EnrollmentStatus> statuses);

    Optional<TrainingEnrollment> findByIdAndUser_Id(UUID enrollmentId, UUID userId);

    @Query("SELECT COUNT(DISTINCT e.user.id) FROM TrainingEnrollment e WHERE e.org = :org AND e.createdAt <= :date")
    long countDistinctTraineesByOrgAndCreatedAtToDate(@Param("org") Organization org, @Param("date") Instant date);

    @Query("""
            SELECT te
            FROM TrainingEnrollment te
            JOIN FETCH te.user u
            JOIN FETCH te.training tr
            WHERE te.org.id = :orgId
              AND (
                :search IS NULL OR :search = ''
                OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(tr.title, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            """)
    Page<TrainingEnrollment> searchProgrammeCompletionByOrganization(
            @Param("orgId") UUID orgId,
            @Param("search") String search,
            Pageable pageable);

    @Query("""
            SELECT te
            FROM TrainingEnrollment te
            WHERE te.org.id = :orgId
              AND te.user.id = :userId
            ORDER BY te.updatedAt DESC
            """)
    List<TrainingEnrollment> findTopByOrgAndUserOrderByUpdatedAtDesc(
            @Param("orgId") UUID orgId,
            @Param("userId") UUID userId,
            Pageable pageable);

    @Query("""
            SELECT COUNT(te)
            FROM TrainingEnrollment te
            WHERE te.org.id = :orgId
              AND te.status <> com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus.COMPLETED
              AND te.progressPercent < :thresholdPercent
              AND te.updatedAt >= :start
              AND te.updatedAt < :end
            """)
    long countLikelyDropOffEnrollmentsByOrgAndBucket(
            @Param("orgId") UUID orgId,
            @Param("thresholdPercent") int thresholdPercent,
            @Param("start") Instant start,
            @Param("end") Instant end);
}
