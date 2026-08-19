package com.ocean.afefe.entities.modules.nse.cpdpathway.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.nse.auth.models.Organization;
import com.ocean.afefe.entities.modules.nse.auth.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity(name = "NseCpdPathwayEnrollment")
@Table(name = "cpd_pathway_enrollments", schema = "afefe_nse")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpdPathwayEnrollment extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pathway_id", nullable = false)
    private CpdPathway pathway;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(40)")
    private CpdPathwayEnrollmentStatus status;

    @Column(nullable = false)
    private Integer progressPercent = 0;

    @Column(length = 200)
    private String paymentSessionReference;

    @Column(length = 200)
    private String paymentGatewayReference;

    private Instant paidAt;

    private Instant startedAt;

    private Instant completedAt;
}
