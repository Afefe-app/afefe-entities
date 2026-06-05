package com.ocean.afefe.entities.modules.trainings;

import com.ocean.afefe.entities.modules.trainings.service.TrainingContentNoteObjectValidatorImpl;
import com.ocean.afefe.entities.modules.trainings.service.TrainingDomainServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TrainingDomainServiceImpl.class, TrainingContentNoteObjectValidatorImpl.class})
public class TrainingsModule {}
