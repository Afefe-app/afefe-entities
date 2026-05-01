package com.ocean.afefe.entities.modules.trainings.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.data.CourseProposedAchievement;
import com.ocean.afefe.entities.modules.contents.models.CourseLevel;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Training extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(nullable = false)
    private String coverImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirement;

    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String learningOutcomeJsonList;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String titleHash;

    @Column(nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Builder.Default
    private String level = CourseLevel.INTERMEDIATE.name();

    private String language;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainingStatus status;

    private Integer estimatedMinutes;

    private Integer priceCents;

    @Column(nullable = false)
    private boolean free;

    @Builder.Default
    private double rating = 0;

    @Builder.Default
    private int reviews = 0;

    private Instant publishedAt;

    @Builder.Default
    private boolean hasCertificate = true;

    /** Optional external programme identifier from authoring UI */
    private String programmeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;

    @Override
    public void prePersist() {
        super.prePersist();
        if (CommonUtil.isNullOrEmpty(this.getSlug())) {
            this.setSlug(CommonUtil.generateSlug("T"));
        }
        if (CommonUtil.isNullOrEmpty(this.getTitleHash())) {
            this.setTitleHash(buildTitleHash(this.getTitle()));
        }
    }

    @SneakyThrows
    public Set<String> getRequirementSet() {
        return CommonUtil.isNullOrEmpty(this.getRequirement()) ? new HashSet<>()
                : CommonUtil.getServerMapper().readValue(this.getRequirement(), new TypeReference<Set<String>>() {});
    }

    @SneakyThrows
    public List<CourseProposedAchievement> getProposedAchievements() {
        return CommonUtil.isNullOrEmpty(this.getLearningOutcomeJsonList())
                ? new ArrayList<>()
                : CommonUtil.getServerMapper()
                        .readValue(this.getLearningOutcomeJsonList(), new TypeReference<List<CourseProposedAchievement>>() {});
    }

    @SneakyThrows
    public Set<String> getTagSet() {
        return CommonUtil.isNullOrEmpty(this.getTags()) ? new HashSet<>()
                : CommonUtil.getServerMapper().readValue(this.getTags(), new TypeReference<Set<String>>() {});
    }

    public static String buildTitleHash(String title) {
        return title.replace(StringValues.SINGLE_SPACE, StringValues.EMPTY_STRING);
    }
}
