package com.ocean.afefe.entities.modules.taxonomy.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(
        name = "categories"
)
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseUUIDEntity {

    private String name;

    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;
}
