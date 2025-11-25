package com.ocean.afefe.entities.modules.contents.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "instructors",
       uniqueConstraints = @UniqueConstraint(
               name = "uk_instructors_org_user",
               columnNames = {"org_id","user_id"}
       ),
       indexes = {
           @Index(name = "idx_instructors_org_id", columnList = "org_id"),
           @Index(name = "idx_instructors_user_id", columnList = "user_id")
       })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    private String displayName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String websiteUrl;
}
