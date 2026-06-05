package com.ocean.afefe.entities.modules.trainings.service;

import com.ocean.afefe.entities.modules.trainings.models.*;
import com.ocean.afefe.entities.modules.trainings.repository.*;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrainingContentNoteObjectValidatorImpl implements TrainingContentNoteObjectValidator {

    private final TrainingRepository trainingRepository;
    private final TrainingMonthRepository trainingMonthRepository;
    private final TrainingWeekRepository trainingWeekRepository;
    private final TrainingContentItemRepository trainingContentItemRepository;
    private final TrainingContentBlockRepository trainingContentBlockRepository;

    @Override
    public Training requireObjectInOrg(TrainingContentNoteObjectType objectType, UUID objectId, UUID orgId) {
        Training training =
                switch (objectType) {
                    case TRAINING -> trainingRepository
                            .findByIdAndOrg_Id(objectId, orgId)
                            .orElse(null);
                    case MONTH -> trainingMonthRepository
                            .findById(objectId)
                            .map(TrainingMonth::getTraining)
                            .filter(t -> t.getOrg().getId().equals(orgId))
                            .orElse(null);
                    case WEEK -> trainingWeekRepository
                            .findById(objectId)
                            .map(TrainingWeek::getMonth)
                            .map(TrainingMonth::getTraining)
                            .filter(t -> t.getOrg().getId().equals(orgId))
                            .orElse(null);
                    case CONTENT_ITEM -> trainingContentItemRepository
                            .findById(objectId)
                            .map(TrainingContentItem::getWeek)
                            .map(TrainingWeek::getMonth)
                            .map(TrainingMonth::getTraining)
                            .filter(t -> t.getOrg().getId().equals(orgId))
                            .orElse(null);
                    case BLOCK -> trainingContentBlockRepository
                            .findById(objectId)
                            .map(TrainingContentBlock::getContentItem)
                            .map(TrainingContentItem::getWeek)
                            .map(TrainingWeek::getMonth)
                            .map(TrainingMonth::getTraining)
                            .filter(t -> t.getOrg().getId().equals(orgId))
                            .orElse(null);
                };

        if (training == null) {
            throw HttpUtil.getResolvedException(
                    ResponseCode.RECORD_NOT_FOUND,
                    objectType.getDescription() + " not found with id: " + objectId);
        }
        return training;
    }
}
