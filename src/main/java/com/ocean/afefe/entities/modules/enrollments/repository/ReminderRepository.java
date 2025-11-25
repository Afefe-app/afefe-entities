package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.enrollments.models.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder, UUID> {
}
