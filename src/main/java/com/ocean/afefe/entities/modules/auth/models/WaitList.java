package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitList extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;
}
