package com.ocean.afefe.entities.modules.nse.cpdpathway.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "NseCpdPathwayCourse")
@Table(name = "cpd_pathway_courses", schema = "afefe_nse")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpdPathwayCourse extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pathway_id", nullable = false)
    private CpdPathway pathway;

    @Column(nullable = false)
    private String title;

    @Column
    private String instructorName;

    @Column(columnDefinition = "TEXT")
    private String coverImageUrl;

    @Column
    private Double rating;

    @Column
    private Integer reviewCount;

    @Column
    private Integer durationMinutes;

    @Column(nullable = false)
    private Integer position;
}
