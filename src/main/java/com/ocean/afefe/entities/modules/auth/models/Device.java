package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Table(name = "devices",
       indexes = @Index(name = "idx_devices_user_id", columnList = "user_id"))
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String deviceType;

    private String platform;

    private String pushToken;

    private Instant lastSeenAt;
}
