package com.ocean.afefe.entities.modules.notifications.model;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "org_notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgNotification extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String bodyHtml;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private OrgNotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private OrgNotificationStatus status;

    @Column(nullable = false)
    @Builder.Default
    private boolean sendToAll = false;

    /** When set, email is queued until this instant (inclusive) by the scheduler. */
    private Instant scheduledAt;

    private Instant sentAt;

    @Column(length = 2000)
    private String lastError;

    @Builder.Default
    @OneToMany(mappedBy = "orgNotification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrgNotificationRecipient> recipients = new ArrayList<>();
}
