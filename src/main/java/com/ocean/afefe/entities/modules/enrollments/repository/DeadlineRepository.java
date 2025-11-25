package com.ocean.afefe.entities.modules.enrollments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeadlineRepository extends JpaRepository<DeadlineRepository, UUID> {
}
