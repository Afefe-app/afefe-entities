package com.ocean.afefe.entities.modules.nse.auth.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByUser_Id(UUID userId);
}
