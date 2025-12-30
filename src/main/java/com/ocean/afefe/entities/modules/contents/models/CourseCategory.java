package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.taxonomy.models.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCategory extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
