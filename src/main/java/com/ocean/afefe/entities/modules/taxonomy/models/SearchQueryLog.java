package com.ocean.afefe.entities.modules.taxonomy.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "search_queries_log",
        indexes = {
                @Index(name = "idx_search_queries_org_id", columnList = "org_id"),
                @Index(name = "idx_search_queries_user_id", columnList = "user_id"),
                @Index(name = "idx_search_queries_executed_at", columnList = "executed_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryLog extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization org;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "query_text", columnDefinition = "text", nullable = false)
    private String queryText;

    @Column(name = "filters_json", columnDefinition = "json")
    private String filtersJson;

    @Column(name = "results_count", nullable = false)
    private Integer resultsCount = 0;

    @Column(name = "executed_at", nullable = false)
    private LocalDateTime executedAt;
}
