package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.common.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOtpAuth extends BaseUUIDEntity {

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String otpHash;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserOtpAction action;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;
}
