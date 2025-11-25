package com.ocean.afefe.entities.modules.taxonomy.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(
        name = "categories",
       uniqueConstraints = {
               @UniqueConstraint(
                       name = "uk_parent_category_name",
                       columnNames = {"parent_category_id", "name"}
               )
       },
        indexes = {
                @Index(
                        name = "idx_category_parent_id", columnList = "parent_category_id"
                )
        }
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
