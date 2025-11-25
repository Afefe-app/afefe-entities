package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(
        name = "content_licenses",
        indexes = {
                @Index(name = "idx_content_licenses_course_id", columnList = "course_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentLicense extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentLicenseType licenseType;

    private String termsUrl;

    private Instant validFrom;

    private Instant validTo;
}
