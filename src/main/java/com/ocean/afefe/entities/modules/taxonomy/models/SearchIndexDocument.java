package com.ocean.afefe.entities.modules.taxonomy.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "search_index_documents"
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchIndexDocument extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;

    @Column(name = "object_type", nullable = false)
    private String objectType;

    @Column(name = "object_id", nullable = false)
    private String objectId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String snippet;

    private String locale;
}
