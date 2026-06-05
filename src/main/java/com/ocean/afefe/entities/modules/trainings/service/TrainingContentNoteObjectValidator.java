package com.ocean.afefe.entities.modules.trainings.service;

import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingContentNoteObjectType;

import java.util.UUID;

public interface TrainingContentNoteObjectValidator {

    Training requireObjectInOrg(TrainingContentNoteObjectType objectType, UUID objectId, UUID orgId);
}
