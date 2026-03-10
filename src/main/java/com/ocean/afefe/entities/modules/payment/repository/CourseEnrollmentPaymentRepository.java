package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import com.ocean.afefe.entities.modules.payment.model.CourseEnrollmentPayment;
import com.ocean.afefe.entities.modules.payment.model.PaymentTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseEnrollmentPaymentRepository extends JpaRepository<CourseEnrollmentPayment, UUID>, QuerydslPredicateExecutor<CourseEnrollmentPayment> {

    Optional<CourseEnrollmentPayment> findByUserAndCourse(User user, Course course);

    Optional<CourseEnrollmentPayment> findFirstByPaymentTransaction(PaymentTransaction paymentTransaction);

    List<CourseEnrollmentPayment> findByUserOrderByCreatedAtDesc(User user);

    List<CourseEnrollmentPayment> findByCourseOrderByCreatedAtDesc(Course course);

    Optional<CourseEnrollmentPayment> findByEnrollment(Enrollment enrollment);

        @Query("""
        select cep
        from CourseEnrollmentPayment cep
        where cep.paymentTransaction is not null
          and cep.paymentTransaction.status = 'SUCCESS'
          and cep.enrollment is null
    """)
    Page<CourseEnrollmentPayment> findAllCourseEnrollPaymentPendingCourseEnrollment(Pageable pageable);

    boolean existsByUserAndCourse(User user, Course course);
}
