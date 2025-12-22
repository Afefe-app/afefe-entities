package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Locale;

@Entity
@Table(name = "courses",
       indexes = {
           @Index(name = "idx_courses_org_id", columnList = "org_id"),
           @Index(name = "idx_courses_owner_instructor_id", columnList = "owner_instructor_id")
       },
       uniqueConstraints = @UniqueConstraint(name = "uk_courses_slug", columnNames = "slug"))
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

    private String coverImageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirement;

    private BigDecimal price = BigDecimal.ZERO;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Currency currency = Currency.NGN;

    @Column(columnDefinition = "TEXT")
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String learningOutcomeJsonList;  // Array<CourseProposedAchievement>

    @Column(nullable = false)
    private String title;

    private String titleHash;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String level = StringValues.EMPTY_STRING;

    private String language = Locale.ENGLISH.getDisplayName();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CourseStatus status;

    private Integer estimatedMinutes = 60;

    private Integer priceCents = 0;

    @Column(nullable = false)
    private boolean free = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;

    public void prePersist(){
        super.prePersist();
        if(CommonUtil.isNullOrEmpty(this.getTitleHash())){
            this.setTitleHash(this.getTitle().replace(StringValues.SINGLE_SPACE, StringValues.EMPTY_STRING));
        }
        if(CommonUtil.isNullOrEmpty(this.getSlug())){
            this.setSlug(CommonUtil.generateGuid());
        }
    }
}
