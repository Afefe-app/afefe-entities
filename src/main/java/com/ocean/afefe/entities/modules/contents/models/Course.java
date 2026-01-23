package com.ocean.afefe.entities.modules.contents.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.data.CourseProposedAchievement;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor ownerInstructor;

    @Column(nullable = false)
    private String coverImageUrl;

    @Column(nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirement;

    private BigDecimal price = BigDecimal.ZERO;

    @Enumerated(value = EnumType.STRING)
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

    @Column( columnDefinition = "TEXT")
    private String summary;

    private String level;

    private String language;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CourseStatus status;

    private Integer estimatedMinutes;

    private Integer priceCents;

    @Column(nullable = false)
    private boolean free;

    @Builder.Default
    private double rating = 0;

    @Builder.Default
    private int reviews = 0;

    private Instant viewedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;


    @Override
    public void prePersist(){
        super.prePersist();
        if(CommonUtil.isNullOrEmpty(this.getSlug())){
            this.setSlug(CommonUtil.generateSlug("C"));
        }
        if(CommonUtil.isNullOrEmpty(this.getTitleHash())){
            this.setTitleHash(buildTitleHash(this.getTitle()));
        }
    }

    @SneakyThrows
    public Set<String> getTagSet(){
        return CommonUtil.isNullOrEmpty(this.getTags()) ? new HashSet<>() : CommonUtil.getServerMapper().readValue(this.getTags(), new TypeReference<Set<String>>() {
        });
    }

    @SneakyThrows
    public List<CourseProposedAchievement> getProposedAchievements(){
        return CommonUtil.isNullOrEmpty(this.getLearningOutcomeJsonList()) ? new ArrayList<>() : CommonUtil.getServerMapper().readValue(this.getLearningOutcomeJsonList(), new TypeReference<List<CourseProposedAchievement>>() {
        });
    }

    @SneakyThrows
    public Set<String> getRequirementSet(){
        return CommonUtil.isNullOrEmpty(this.getRequirement()) ? new HashSet<>() : CommonUtil.getServerMapper().readValue(this.getRequirement(), new TypeReference<Set<String>>() {
        });
    }

    public static String buildTitleHash(String title){
        return title.replace(StringValues.SINGLE_SPACE, StringValues.EMPTY_STRING);
    }
}
