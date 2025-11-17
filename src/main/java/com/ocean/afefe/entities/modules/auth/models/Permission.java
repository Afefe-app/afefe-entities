package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission extends BaseEntity {

    private String key;
    private String description;
}
