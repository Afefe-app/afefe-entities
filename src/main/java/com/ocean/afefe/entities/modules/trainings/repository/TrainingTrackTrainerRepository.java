package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.TrainingTrackTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingTrackTrainerRepository extends JpaRepository<TrainingTrackTrainer, UUID> {

    List<TrainingTrackTrainer> findByTrack_IdAndOrg_IdOrderByCreatedAtDesc(UUID trackId, UUID orgId);

    Optional<TrainingTrackTrainer> findByTrack_IdAndUser_IdAndOrg_Id(UUID trackId, UUID userId, UUID orgId);

    boolean existsByTrack_IdAndUser_IdAndOrg_Id(UUID trackId, UUID userId, UUID orgId);

    long countByTrack_IdAndOrg_Id(UUID trackId, UUID orgId);

    long countByTrack_IdAndOrg_IdAndActiveTrue(UUID trackId, UUID orgId);

    @Query("""
            select ttt.track.id, count(ttt)
            from TrainingTrackTrainer ttt
            where ttt.org.id = :orgId and ttt.track.id in :trackIds
            group by ttt.track.id
            """)
    List<Object[]> countByTrackIds(@Param("orgId") UUID orgId, @Param("trackIds") List<UUID> trackIds);

    Optional<TrainingTrackTrainer> findByIdAndTrack_IdAndOrg_Id(UUID id, UUID trackId, UUID orgId);

    void deleteByTrack_IdAndUserAndOrg_Id(UUID trackId, User user, UUID orgId);
}
