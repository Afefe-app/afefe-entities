package com.ocean.afefe.entities.modules.auth.models;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SSOProvider extends BaseUUIDEntity {

    @Column(nullable = false,  unique = true)
    private String key;

    private String displayName;

    private String protocol;
}
