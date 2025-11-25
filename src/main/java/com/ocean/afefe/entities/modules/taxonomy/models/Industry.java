package com.ocean.afefe.entities.modules.taxonomy.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "industries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Industry extends BaseUUIDEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;
}
