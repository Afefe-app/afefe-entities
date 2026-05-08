package com.ocean.afefe.entities.modules.talentpool.repository;

import com.ocean.afefe.entities.modules.talentpool.models.TalentPoolProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TalentPoolProfileRepository extends JpaRepository<TalentPoolProfile, UUID> {

    Optional<TalentPoolProfile> findByUser_Id(UUID userId);

    List<TalentPoolProfile> findByUser_IdIn(List<UUID> userIds);
}
