package com.ocean.afefe.entities.modules.enrollments.repository;
import com.ocean.afefe.entities.modules.enrollments.models.Enrollment;
import com.ocean.afefe.entities.modules.enrollments.models.ResumePoint;
import com.ocean.afefe.entities.modules.contents.models.LessonAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResumePointRepository extends JpaRepository<ResumePoint, UUID> {

    Optional<ResumePoint> findByEnrollmentAndLessonAsset(Enrollment enrollment, LessonAsset lessonAsset);
}
