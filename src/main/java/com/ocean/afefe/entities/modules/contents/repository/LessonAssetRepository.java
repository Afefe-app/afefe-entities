package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.contents.models.LessonAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonAssetRepository extends JpaRepository<LessonAsset, UUID> {

    List<LessonAsset> findAllByLessonId(UUID lessonId);
}
