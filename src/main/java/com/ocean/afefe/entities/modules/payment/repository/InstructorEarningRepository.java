package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.ocean.afefe.entities.modules.payment.model.InstructorEarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface InstructorEarningRepository extends JpaRepository<InstructorEarning, UUID> {

    @EntityGraph(attributePaths = { "instructor", "enrollmentPayment" })
    Page<InstructorEarning> findAllByInstructor(Instructor instructor, Pageable pageable);

    @EntityGraph(attributePaths = { "instructor", "enrollmentPayment" })
    List<InstructorEarning> findAllByInstructorAndCreatedAtBetween(Instructor instructor, Instant startDate, Instant endDate);

    @Query(value = """
        select coalesce(sum(ie.baseEarning), 0) 
        from InstructorEarning ie 
        where ie.instructor = :instructor 
        and ie.createdAt 
        between :startDate and :endDate
        and ie.status = 'COMPLETED'
    """)
    BigDecimal getTotalEarningBetween(@Param("instructor") Instructor instructor, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

    @Query(value = """
        select coalesce(sum(ie.baseEarning), 0) 
        from InstructorEarning ie 
        where ie.instructor = :instructor 
        and ie.status = 'COMPLETED'
    """)
    BigDecimal getTotalEarningNow(@Param("instructor") Instructor instructor);

    @Query(value = """
        select coalesce(sum(ie.baseEarning), 0) 
        from InstructorEarning ie 
        where ie.instructor = :instructor 
        and ie.createdAt <= :date
        and ie.status = 'COMPLETED'
    """)
    BigDecimal getTotalEarningToDate(@Param("instructor") Instructor instructor, @Param("date") Instant date);
}
