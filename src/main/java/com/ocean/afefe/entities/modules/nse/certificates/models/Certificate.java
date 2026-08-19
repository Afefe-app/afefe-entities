package com.ocean.afefe.entities.modules.nse.certificates.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.nse.auth.models.Organization;
import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.ocean.afefe.entities.modules.nse.trainings.models.Training;
import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingEnrollment;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
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
import lombok.SneakyThrows;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "NseCertificate")
@Table(name = "certificates", schema = "afefe_nse")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private Training training;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    private TrainingEnrollment enrollment;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String certificateNumber;

    @Column(nullable = false)
    private String candidateName;

    @Column(nullable = false)
    private String issuingBody;

    @Column(columnDefinition = "TEXT")
    private String aboutCertificate;

    @Column(columnDefinition = "TEXT")
    private String skillsJson;

    @Column(columnDefinition = "TEXT")
    private String certificateUrl;

    @Column(columnDefinition = "TEXT")
    private String webAddress;

    @Column(columnDefinition = "TEXT")
    private String thirdPartyObtainedFrom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR")
    private CertificateSource source;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR")
    private CertificateStatus status;

    @Column(nullable = false)
    private Instant issuedAt;

    private Instant expiresAt;

    private Instant revokedAt;

    @Column(columnDefinition = "TEXT")
    private String revocationReason;

    private LocalDate dateIssued;

    private LocalDate expiryDate;

    @SneakyThrows
    public List<String> getSkills() {
        if (CommonUtil.isNullOrEmpty(skillsJson)) {
            return new ArrayList<>();
        }
        return CommonUtil.getServerMapper().readValue(skillsJson, new TypeReference<List<String>>() {});
    }

    @SneakyThrows
    public void setSkills(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            this.skillsJson = null;
            return;
        }
        this.skillsJson = CommonUtil.getServerMapper().writeValueAsString(skills);
    }
}
