package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.ocean.afefe.entities.modules.payment.model.InstructorPayoutLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Repository
public interface InstructorPayoutLogRepository extends JpaRepository<InstructorPayoutLog, UUID> {

    Page<InstructorPayoutLog> findAllByInstructor(Instructor instructor, Pageable pageable);

        @Query("""
        select coalesce(sum(pl.payoutAmount), 0)
        from InstructorPayoutLog pl
        where pl.instructor = :instructor
          and pl.createdAt between :startDate and :endDate
    """)
        BigDecimal getTotalPayoutBetween(
                @Param("instructor") Instructor instructor,
                @Param("startDate") Instant startDate,
                @Param("endDate") Instant endDate
        );

    @Query("""
        select coalesce(sum(pl.payoutAmount), 0)
        from InstructorPayoutLog pl
        where pl.instructor = :instructor
    """)
    BigDecimal getTotalPayoutNow(@Param("instructor") Instructor instructor);

    @Query("""
        select coalesce(sum(pl.payoutAmount), 0)
        from InstructorPayoutLog pl
        where pl.instructor = :instructor
        and pl.createdAt <= :date
    """)
    BigDecimal getTotalPayoutToDate(@Param("instructor") Instructor instructor, @Param("date") Instant date);
}
