package com.ocean.afefe.entities.modules.trainings.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.ojt.OjtSessionDisplayId;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "trainee_ojt_session_log",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_trainee_ojt_session_log_display_id", columnNames = "display_session_id")
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeOjtSessionLog extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_enrollment_id", nullable = false)
    private TrainingEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_user_id")
    private User supervisorUser;

    @Column(nullable = false)
    private String supervisorName;

    @Column(nullable = false)
    private String sessionLocation;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Column(nullable = false)
    private Integer durationHours;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @Column(nullable = false)
    private boolean liveLocationUsed = false;

    private BigDecimal submittedLatitude;

    private BigDecimal submittedLongitude;

    @Column(columnDefinition = "TEXT")
    private String supportingDocumentsJson;

    @Column(nullable = false)
    private String displaySessionId;

    @Override
    public void prePersist() {
        super.prePersist();
        if (CommonUtil.isNullOrEmpty(displaySessionId) && !CommonUtil.isNullOrEmpty(getGuid())) {
            displaySessionId = OjtSessionDisplayId.fromGuid(getGuid());
        }
    }
}
