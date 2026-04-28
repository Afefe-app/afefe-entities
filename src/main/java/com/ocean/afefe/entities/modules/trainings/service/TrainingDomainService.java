package com.ocean.afefe.entities.modules.trainings.service;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;

import java.util.UUID;

public interface TrainingDomainService {

    Training requirePublishedTraining(UUID trainingId, Organization organization);

    Training requireTrainingInOrg(UUID trainingId, Organization organization);

    TrainingEnrollment requireEnrollmentForUser(UUID enrollmentId, User user);
}
