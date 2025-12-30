package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "courses",
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

    @Column(nullable = false, unique = true)
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
    }
}
