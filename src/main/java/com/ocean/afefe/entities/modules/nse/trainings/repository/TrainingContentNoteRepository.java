package com.ocean.afefe.entities.modules.nse.trainings.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.Organization;
import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.ocean.afefe.entities.modules.nse.trainings.models.TrainingContentNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingContentNoteRepository extends JpaRepository<TrainingContentNote, UUID> {

    List<TrainingContentNote> findByUserAndOrgAndTraining_IdOrderByUpdatedAtDesc(
            User user, Organization org, UUID trainingId);

    Optional<TrainingContentNote> findByIdAndUserAndOrgAndTraining_Id(
            UUID noteId, User user, Organization org, UUID trainingId);
}
