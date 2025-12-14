package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.tensorpoint.toolkit.tpointcore.commons.TimeZone;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(
        name = "user_profiles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_profile_user",
                        columnNames = {"user_id"}
                )
        },
        indexes = {
                @Index(name = "idx_user_profile_user_id", columnList = "user_id")
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String displayName;

    private String avatarUrl;

    private String phone;

    private String locale;

    private TimeZone timeZone;

    private String jobTitle;
}
