package com.ocean.afefe.entities.modules.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseEnrollmentPayment extends BaseUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_transaction_id", nullable = false)
    private PaymentTransaction paymentTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    private Instant paidAt;

    public boolean isCompleted(){
        return Objects.nonNull(paymentTransaction) && Objects.nonNull(paymentTransaction.getStatus()) && PaymentStatus.SUCCESS.equals(paymentTransaction.getStatus());
    }
}
