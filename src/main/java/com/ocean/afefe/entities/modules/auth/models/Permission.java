package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(
        name = "permissions",
        indexes = {
                @Index(name = "idx_permission_role_id", columnList = "role_id")
        }
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private String key;
    private String description;
}
