package com.ocean.afefe.entities.modules.taxonomy.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "job_roles"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRole extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @Column(nullable = false)
    private String name;

    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

}
