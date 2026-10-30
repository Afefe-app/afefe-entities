package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingTrack;
import com.ocean.afefe.entities.modules.trainings.models.TrainingTrackStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingTrackRepository extends JpaRepository<TrainingTrack, UUID> {

    List<TrainingTrack> findByTraining_IdAndOrg_IdOrderByCreatedAtDesc(UUID trainingId, UUID orgId);

    Page<TrainingTrack> findByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId, Pageable pageable);

    Optional<TrainingTrack> findByIdAndOrg_Id(UUID id, UUID orgId);

    Optional<TrainingTrack> findByIdAndTraining_IdAndOrg_Id(UUID id, UUID trainingId, UUID orgId);

    boolean existsByTraining_IdAndNameIgnoreCaseAndOrg_Id(UUID trainingId, String name, UUID orgId);

    long countByTraining_IdAndOrg_Id(UUID trainingId, UUID orgId);

    long countByTraining_IdAndOrg_IdAndStatus(UUID trainingId, UUID orgId, TrainingTrackStatus status);

    @Query("""
            select t from TrainingTrack t
            where t.org.id = :orgId
              and t.training.id = :trainingId
              and (:search is null or :search = '' or lower(t.name) like lower(concat('%', cast(:search as string), '%'))
                   or lower(coalesce(t.code, '')) like lower(concat('%', cast(:search as string), '%')))
            order by t.createdAt desc
            """)
    List<TrainingTrack> searchByTraining(
            @Param("orgId") UUID orgId,
            @Param("trainingId") UUID trainingId,
            @Param("search") String search);

    @Query("""
            select t.training.id, count(t)
            from TrainingTrack t
            where t.org.id = :orgId and t.training in :trainings
            group by t.training.id
            """)
    List<Object[]> countByTrainings(@Param("orgId") UUID orgId, @Param("trainings") List<Training> trainings);
}
