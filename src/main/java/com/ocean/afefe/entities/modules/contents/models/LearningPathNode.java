package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "learning_path_nodes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_learningpath_position",
                        columnNames = {"learning_path_id", "position"}
                ),
                @UniqueConstraint(
                        name = "uk_learningpath_course",
                        columnNames = {"learning_path_id", "course_id"}
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningPathNode extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_path_id", nullable = false)
    private LearningPath learningPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private int position;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

}
