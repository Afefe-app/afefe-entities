package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructors",
       uniqueConstraints = @UniqueConstraint(name = "uk_instructors_org_user", columnNames = {"org_id","user_id"}),
       indexes = {
           @Index(name = "idx_instructors_org_id", columnList = "org_id"),
           @Index(name = "idx_instructors_user_id", columnList = "user_id")
       })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Instructor extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    @Column(name = "display_name", length = 255)
    private String displayName;

    @Lob
    @Column(name = "bio", columnDefinition = "text")
    private String bio;

    @Column(name = "website_url", length = 500)
    private String websiteUrl;
}
