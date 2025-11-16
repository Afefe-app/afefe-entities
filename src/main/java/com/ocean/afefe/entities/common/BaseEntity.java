package com.ocean.afefe.entities.common;

import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String guid;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist()
    public void prePersist() {
        if (CommonUtil.isNullOrEmpty(this.getGuid())) {
            this.setGuid(CommonUtil.generateGuid());
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
