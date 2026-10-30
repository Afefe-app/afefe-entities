package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.TrainingTrackCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingTrackCourseRepository extends JpaRepository<TrainingTrackCourse, UUID> {

    List<TrainingTrackCourse> findByTrack_IdAndOrg_IdOrderByPositionAscCreatedAtAsc(UUID trackId, UUID orgId);

    Optional<TrainingTrackCourse> findByIdAndTrack_IdAndOrg_Id(UUID id, UUID trackId, UUID orgId);

    Optional<TrainingTrackCourse> findByTrack_IdAndCourse_IdAndOrg_Id(UUID trackId, UUID courseId, UUID orgId);

    boolean existsByTrack_IdAndCourse_IdAndOrg_Id(UUID trackId, UUID courseId, UUID orgId);

    long countByTrack_IdAndOrg_Id(UUID trackId, UUID orgId);

    @Query("""
            select ttc.track.id, count(ttc)
            from TrainingTrackCourse ttc
            where ttc.org.id = :orgId and ttc.track.id in :trackIds
            group by ttc.track.id
            """)
    List<Object[]> countByTrackIds(@Param("orgId") UUID orgId, @Param("trackIds") List<UUID> trackIds);

    void deleteByTrack_IdAndCourse_IdAndOrg_Id(UUID trackId, UUID courseId, UUID orgId);
}
