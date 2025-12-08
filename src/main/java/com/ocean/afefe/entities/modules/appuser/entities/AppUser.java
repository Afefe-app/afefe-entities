package com.ocean.afefe.entities.modules.appuser.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.modules.appuser.enums.AppUserStatus;
import com.tensorpoint.toolkit.tpointcore.commons.CommonUtil;
import com.tensorpoint.toolkit.tpointcore.commons.EncryptionAlgorithm;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import com.tensorpoint.toolkit.tpointcore.date.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String channelId;

    @Column(nullable = false)
    private String channelSecret;

    @Column(nullable = false)
    private String channelName;

    @Column(nullable = false)
    private String coreUser;

    @Column(nullable = false)
    private String corePassword;

    @Column(nullable = false)
    private String coreTenant;

    private String remitOneAccessUser;

    private String remitOneAccessPassword;

    private String remitOneAccessPin;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AppUserStatus status;

    @Column
    private boolean encryptionRequired;
    private String encryptionKey;

    @Enumerated(value = EnumType.STRING)
    private EncryptionAlgorithm encryptionAlgorithm;

    private boolean deviceAuthRequired;
    private String docUsername;
    private String docPassword;

    @Column(nullable = false, unique = true)
    private String channel;

    private boolean ipAddressCheck;
    private String connectingIPs;

    @PrePersist
    public void prePersistDateTime(){
        if(Objects.isNull(this.getCreatedAt())){
            this.setCreatedAt(DateUtils.getCurrentDateTime());
        }
        if(Objects.isNull(this.getUpdatedAt())){
            this.setUpdatedAt(DateUtils.getCurrentDateTime());
        }
    }

    public Set<String> getConnectingIPSet(){
        return Arrays.stream(this.getConnectingIPs().split(StringValues.COMMA))
                .map(String::trim)
                .filter(CommonUtil::noneNullAndEmpty)
                .collect(Collectors.toSet());
    }
}
