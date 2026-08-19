package com.ocean.afefe.entities.modules.nse.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.tensorpoint.toolkit.tpointcore.commons.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity(name = "NseUser")
@Builder
@Table(name = "users", schema = "afefe_nse")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseUUIDEntity {

    @Column(nullable = false)
    private String emailAddress;

    private String fullName;

    private String avatarUrl;

    private String passwordHash;

    @Column(nullable = false, columnDefinition = "VARCHAR")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private Country country = Country.NIGERIA;

    private boolean isActive;

    private Instant lastLoginAt;
}
