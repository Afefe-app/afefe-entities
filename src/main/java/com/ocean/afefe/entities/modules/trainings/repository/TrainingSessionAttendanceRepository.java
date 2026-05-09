package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import com.ocean.afefe.entities.modules.trainings.models.TrainingSessionAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingSessionAttendanceRepository extends JpaRepository<TrainingSessionAttendance, UUID> {

    Optional<TrainingSessionAttendance> findByEnrollmentAndCalendarEvent_Id(
            TrainingEnrollment enrollment, UUID calendarEventId);

    Page<TrainingSessionAttendance> findByEnrollment_IdOrderByMarkedAtDesc(UUID enrollmentId, Pageable pageable);
}
