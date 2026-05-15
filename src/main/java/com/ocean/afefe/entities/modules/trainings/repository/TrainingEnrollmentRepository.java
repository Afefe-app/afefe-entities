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

    List<TrainingEnrollment> findByOrgAndTraining_Id(Organization org, UUID trainingId);

    long countByOrgAndTraining_Id(Organization org, UUID trainingId);

    List<TrainingEnrollment> findByOrgAndUser_IdIn(Organization org, List<UUID> userIds);

    List<TrainingEnrollment> findByOrgAndUser_IdOrderByUpdatedAtDesc(Organization org, UUID userId);

    @Query("""
            SELECT e.currentMonth.id, COUNT(DISTINCT e.user.id)
            FROM TrainingEnrollment e
            WHERE e.training.id = :trainingId
              AND e.org = :org
              AND e.status <> com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus.COMPLETED
            GROUP BY e.currentMonth.id
            """)
    List<Object[]> countDropOffTraineesByMonthAndTrainingIdAndOrg(
            @Param("trainingId") UUID trainingId,
            @Param("org") Organization org);

    @Query("""
            SELECT e
            FROM TrainingEnrollment e
            JOIN FETCH e.user
            JOIN FETCH e.training
            WHERE e.org = :org
            """)
    Page<TrainingEnrollment> findAllByOrgWithUserAndTraining(
            @Param("org") Organization org, Pageable pageable);

    @Query("""
            SELECT e
            FROM TrainingEnrollment e
            JOIN FETCH e.user
            JOIN FETCH e.training
            WHERE e.org = :org
              AND (
                LOWER(COALESCE(e.user.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(e.user.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(e.training.title, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(e.training.programmeId, '')) LIKE LOWER(CONCAT('%', :search, '%'))
              )
            """)
    Page<TrainingEnrollment> searchAllByOrgWithUserAndTraining(
            @Param("org") Organization org,
            @Param("search") String search,
            Pageable pageable);
}
