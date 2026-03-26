package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreference extends BaseUUIDEntity {

    @ManyToOne(optional = false)
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
