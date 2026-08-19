package com.ocean.afefe.entities.modules.nse.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.tensorpoint.toolkit.tpointcore.commons.TimeZone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "NseUserProfile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "afefe_nse")
public class UserProfile extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    private User user;

    private String displayName;

    private String phone;

    private String locale;

    private TimeZone timeZone;

    private String jobTitle;

    @Column(columnDefinition = "TEXT")
    private String bio;
}
