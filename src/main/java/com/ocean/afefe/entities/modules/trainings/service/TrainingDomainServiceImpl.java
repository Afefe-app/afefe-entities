package com.ocean.afefe.entities.modules.trainings.service;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingEnrollment;
import com.ocean.afefe.entities.modules.trainings.models.TrainingStatus;
import com.ocean.afefe.entities.modules.trainings.repository.TrainingEnrollmentRepository;
import com.ocean.afefe.entities.modules.trainings.repository.TrainingRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingDomainServiceImpl implements TrainingDomainService {

    private final TrainingRepository trainingRepository;
    private final TrainingEnrollmentRepository trainingEnrollmentRepository;

    @Override
    public Training requirePublishedTraining(UUID trainingId, Organization organization) {
        Training training =
                trainingRepository.findByIdAndOrg_Id(trainingId, organization.getId()).orElseThrow(
                        () -> HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, "Training not found"));
        if (training.getStatus() != TrainingStatus.PUBLISHED) {
            throw HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, "Training is not published");
        }
        return training;
    }

    @Override
    public Training requireTrainingInOrg(UUID trainingId, Organization organization) {
        return trainingRepository
                .findByIdAndOrg_Id(trainingId, organization.getId())
                .orElseThrow(() -> HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, "Training not found"));
    }

    @Override
    public TrainingEnrollment requireEnrollmentForUser(UUID enrollmentId, User user) {
        return trainingEnrollmentRepository
                .findByIdAndUser_Id(enrollmentId, user.getId())
                .orElseThrow(() -> HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, "Enrollment not found"));
    }
}
