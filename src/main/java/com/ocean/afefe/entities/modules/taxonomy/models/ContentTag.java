package com.ocean.afefe.entities.modules.taxonomy.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "content_tags"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentTag extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private Tag tag;

    @Column( nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;
}
