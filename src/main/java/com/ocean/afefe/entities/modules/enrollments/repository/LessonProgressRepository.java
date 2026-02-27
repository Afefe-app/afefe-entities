package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.enrollments.models.LessonProgress;
import com.ocean.afefe.entities.modules.contents.models.LessonAsset;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, UUID> {

    Optional<LessonProgress> findByEnrollmentAndLessonAsset(Enrollment enrollment, LessonAsset lessonAsset);
}
