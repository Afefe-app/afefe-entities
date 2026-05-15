package com.ocean.afefe.entities.modules.notifications.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "org_notification_recipients",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_org_notification_recipient",
                columnNames = {"org_notification_id", "user_id"}))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgNotificationRecipient extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_notification_id", nullable = false)
    private OrgNotification orgNotification;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
