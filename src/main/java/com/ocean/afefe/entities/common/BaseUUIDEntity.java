package com.ocean.afefe.entities.common;

import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseUUIDEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String guid;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist()
    public void prePersist() {
        if (CommonUtil.isNullOrEmpty(this.getGuid())) {
            this.setGuid(id.toString().replace(StringValues.HYPHEN, StringValues.EMPTY_STRING));
        }
        if (Objects.isNull(this.getCreatedAt())) {
            this.setCreatedAt(Instant.now());
        }
        if (Objects.isNull(this.getUpdatedAt())) {
            this.setUpdatedAt(Instant.now());
        }
    }

    @PreUpdate()
    public void preUpdate() {
        this.setUpdatedAt(Instant.now());
    }
}
