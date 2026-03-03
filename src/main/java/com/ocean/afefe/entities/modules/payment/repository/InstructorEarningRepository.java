package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.ocean.afefe.entities.modules.payment.model.InstructorEarning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstructorEarningRepository extends JpaRepository<InstructorEarning, UUID> {

    @EntityGraph(attributePaths = { "instructor", "enrollmentPayment" })
    Page<InstructorEarning> findAllByInstructor(Instructor instructor, Pageable pageable);
}
