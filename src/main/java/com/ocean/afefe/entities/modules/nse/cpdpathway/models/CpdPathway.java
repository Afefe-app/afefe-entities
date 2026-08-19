package com.ocean.afefe.entities.modules.nse.cpdpathway.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.nse.auth.models.Organization;
import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
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

import java.math.BigDecimal;

@Entity(name = "NseCpdPathway")
@Table(name = "cpd_pathways", schema = "afefe_nse")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpdPathway extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String coverImageUrl;

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR")
    private Currency currency;

    @Column(nullable = false)
    private Integer amountInMinor = 0;

    @Column(nullable = false)
    private Integer totalModules = 0;

    @Column(nullable = false)
    private Integer totalCourses = 0;

    @Column(nullable = false)
    private Integer cpdPoints = 0;

    @Column(columnDefinition = "TEXT")
    private String includedItemsJson;

    @Column(columnDefinition = "TEXT")
    private String overviewContentJson;

    @Column(columnDefinition = "TEXT")
    private String pathwayContentJson;

    @Column(columnDefinition = "TEXT")
    private String faqsJson;

    @Column(columnDefinition = "TEXT")
    private String reviewsJson;

    @Column(columnDefinition = "TEXT")
    private String instructorsJson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR")
    private CpdPathwayStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;

    @Override
    public void prePersist() {
        super.prePersist();
        if (CommonUtil.isNullOrEmpty(this.slug)) {
            this.slug = CommonUtil.generateSlug("CPD");
        }
    }
}
