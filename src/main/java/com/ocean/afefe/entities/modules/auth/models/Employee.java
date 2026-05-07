package com.ocean.afefe.entities.modules.auth.models;

import com.ocean.afefe.entities.common.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "employees",
        uniqueConstraints = @UniqueConstraint(name = "uk_employees_user_org", columnNames = {"user_id", "org_id"})
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization org;

    private String displayName;

    private String department;

    private String employeeCode;

    private String jobTitle;

    /** Free-text job description from HR provisioning (optional). */
    @Column(columnDefinition = "text")
    private String jobDescription;
}
