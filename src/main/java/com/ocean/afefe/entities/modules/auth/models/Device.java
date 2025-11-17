package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "devices",
       indexes = @Index(name = "idx_devices_user_id", columnList = "user_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Device extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "device_type", length = 50)
    private String deviceType;

    @Column(name = "platform", length = 50)
    private String platform;

    @Column(name = "push_token", length = 500)
    private String pushToken;

    @Column(name = "last_seen_at")
    private OffsetDateTime lastSeenAt;
}
