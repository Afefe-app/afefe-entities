package com.ocean.afefe.entities.core.localstore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppGenericParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(500)")
    private String paramKey;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String paramValue;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist()
    public void prePersist() {
        if (createdAt == null) {
            this.createdAt = Instant.now();
        }
    }
}
