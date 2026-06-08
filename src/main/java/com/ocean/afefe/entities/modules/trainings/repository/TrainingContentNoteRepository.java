package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.TrainingContentNote;
import com.ocean.afefe.entities.modules.trainings.models.TrainingContentNoteObjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingContentNoteRepository extends JpaRepository<TrainingContentNote, UUID> {

    Page<TrainingContentNote> findAllByUserAndOrg(User user, Organization org, Pageable pageable);

    Page<TrainingContentNote> findAllByUserAndOrgAndObjectId(
            User user, Organization org, UUID objectId, Pageable pageable);

    Page<TrainingContentNote> findAllByUserAndOrgAndObjectType(
            User user, Organization org, TrainingContentNoteObjectType objectType, Pageable pageable);

    Page<TrainingContentNote> findAllByUserAndOrgAndObjectIdAndObjectType(
            User user,
            Organization org,
            UUID objectId,
            TrainingContentNoteObjectType objectType,
            Pageable pageable);

    Optional<TrainingContentNote> findByIdAndUser(UUID id, User user);
}
