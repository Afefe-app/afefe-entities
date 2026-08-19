package com.ocean.afefe.entities.modules.nse.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
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

@Getter
@Setter
@Entity(name = "NseTrainee")
@Builder
@Table(name = "trainees", schema = "afefe_nse")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trainee extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private Boolean certified;

    @Column(name = "membership_id")
    private String membershipId;

    @Enumerated(EnumType.STRING)
    @Column(name = "professional_division", columnDefinition = "VARCHAR")
    private ProfessionalDivision professionalDivision;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "onboarding_status", nullable = false, columnDefinition = "VARCHAR")
    private OnboardingStatus onboardingStatus = OnboardingStatus.REGISTERED;

    @Builder.Default
    @Column(name = "terms_accepted", nullable = false)
    private boolean termsAccepted = false;

    private Instant termsAcceptedAt;

    private Instant membershipConfirmedAt;

    private Instant onboardingCompletedAt;
}
