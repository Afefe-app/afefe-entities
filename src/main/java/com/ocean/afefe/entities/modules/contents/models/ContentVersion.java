package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "content_versions"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentVersion extends BaseUUIDEntity {

    @Column(name = "object_type", nullable = false, length = 50)
    private String objectType;

    @Column(name = "object_id", nullable = false)
    private String objectId;

    @Column(nullable = false)
    private Integer versionNum;

    @Column(columnDefinition = "TEXT")
    private String diffJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
}
