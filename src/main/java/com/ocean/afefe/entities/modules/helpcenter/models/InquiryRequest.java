package com.ocean.afefe.entities.modules.helpcenter.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
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

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inquiry_requests")
public class InquiryRequest extends BaseUUIDEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryRequestKind requestKind;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryRequesterType requesterType;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String organizationName;

    @Column(nullable = false)
    private String titleRole;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneCountryCode;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column
    private Integer numberOfUsers;

    @Column(nullable = false)
    @Builder.Default
    private boolean agreedToPrivacyPolicy = false;
}
