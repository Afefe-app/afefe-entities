package com.ocean.afefe.entities.modules.nse.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_preferences", schema = "afefe_nse")
public class NotificationPreference extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Builder.Default
    private boolean updatesAndOfferingsPush = false;

    @Column(nullable = false)
    @Builder.Default
    private boolean updatesAndOfferingsEmail = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean remindersPush = true;

    @Column(nullable = false)
    @Builder.Default
    private boolean remindersEmail = false;
}
