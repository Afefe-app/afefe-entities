package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.tensorpoint.toolkit.tpointcore.commons.TimeZone;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Locale;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    private User user;

    private String displayName;

    private String avatarUrl;

    private String phone;

    private String locale;

    private TimeZone timeZone;

    private String jobTitle;
}
