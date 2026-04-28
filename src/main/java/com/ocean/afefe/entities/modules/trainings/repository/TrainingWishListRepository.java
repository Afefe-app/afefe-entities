package com.ocean.afefe.entities.modules.trainings.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.trainings.models.Training;
import com.ocean.afefe.entities.modules.trainings.models.TrainingWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingWishListRepository extends JpaRepository<TrainingWishList, UUID> {

    List<TrainingWishList> findByUserAndOrgOrderByUpdatedAtDesc(User user, Organization org);

    Optional<TrainingWishList> findByUserAndTraining(User user, Training training);

    boolean existsByUserAndTraining_Id(User user, UUID trainingId);
}
