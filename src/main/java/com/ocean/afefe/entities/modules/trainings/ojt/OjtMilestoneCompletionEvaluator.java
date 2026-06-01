package com.ocean.afefe.entities.modules.trainings.ojt;

import com.ocean.afefe.entities.modules.enrollments.models.EnrollmentStatus;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import com.ocean.afefe.entities.modules.trainings.models.TrainingOjtMilestone;
import com.ocean.afefe.entities.modules.trainings.models.TrainingOjtMilestoneType;
import com.ocean.afefe.entities.modules.trainings.repository.TraineeOjtSessionLogRepository;
import com.tensorpoint.toolkit.tpointcore.date.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OjtMilestoneCompletionEvaluator {

    private final TraineeOjtSessionLogRepository ojtSessionLogRepository;

    public OjtOverviewSnapshot buildOverview(
            TrainingEnrollment enrollment, Training training, List<TrainingOjtMilestone> milestones) {
        long totalHours = ojtSessionLogRepository.sumDurationHoursByEnrollmentId(enrollment.getId());
        return buildOverviewWithTotalHours(enrollment, training, milestones, totalHours);
    }

    public OjtOverviewSnapshot buildOverviewWithTotalHours(
            TrainingEnrollment enrollment,
            Training training,
            List<TrainingOjtMilestone> milestones,
            long totalHoursLogged) {
        Integer daysLeft = computeDaysLeft(enrollment, training);
        List<OjtMilestoneSnapshot> milestoneData = new ArrayList<>();
        LocalDate enrollmentStart = enrollmentStartDate(enrollment);

        for (TrainingOjtMilestone milestone : milestones) {
            LocalDate targetDate =
                    enrollmentStart != null ? enrollmentStart.plusDays(milestone.getOffsetDays()) : null;
            String status = resolveStatus(enrollment, training, milestone, targetDate, totalHoursLogged);
            milestoneData.add(OjtMilestoneSnapshot.builder()
                    .title(milestone.getTitle())
                    .targetDate(targetDate != null ? targetDate.toString() : null)
                    .status(status)
                    .milestoneType(milestone.getMilestoneType().name())
                    .build());
        }

        return OjtOverviewSnapshot.builder()
                .daysLeft(daysLeft)
                .totalHoursLogged(totalHoursLogged)
                .minOjtHours(training.getMinOjtHours())
                .milestones(milestoneData)
                .build();
    }

    private Integer computeDaysLeft(TrainingEnrollment enrollment, Training training) {
        if (training.getOjtDurationDays() == null || enrollment.getStartedAt() == null) {
            return null;
        }
        LocalDate start = enrollmentStartDate(enrollment);
        if (start == null) {
            return null;
        }
        LocalDate end = start.plusDays(training.getOjtDurationDays());
        long days = ChronoUnit.DAYS.between(DateUtils.getCurrentDate(), end);
        return (int) Math.max(0, days);
    }

    private LocalDate enrollmentStartDate(TrainingEnrollment enrollment) {
        Instant started = enrollment.getStartedAt();
        if (started == null) {
            return null;
        }
        return started.atZone(ZoneOffset.UTC).toLocalDate();
    }

    private String resolveStatus(
            TrainingEnrollment enrollment,
            Training training,
            TrainingOjtMilestone milestone,
            LocalDate targetDate,
            long totalHours) {
        if (milestone.getMilestoneType() == TrainingOjtMilestoneType.START) {
            return enrollment.getStartedAt() != null ? "COMPLETED" : "PENDING";
        }
        if (milestone.getMilestoneType() == TrainingOjtMilestoneType.COMPLETION) {
            if (enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
                return "COMPLETED";
            }
            if (training.getMinOjtHours() != null && totalHours >= training.getMinOjtHours()) {
                return "COMPLETED";
            }
            return "PENDING";
        }
        if (targetDate == null) {
            return "PENDING";
        }
        boolean hasLog = ojtSessionLogRepository.existsByEnrollment_IdAndSessionDateGreaterThanEqual(
                enrollment.getId(), targetDate);
        return hasLog ? "COMPLETED" : "PENDING";
    }
}
