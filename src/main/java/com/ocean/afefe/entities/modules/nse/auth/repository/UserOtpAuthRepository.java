package com.ocean.afefe.entities.modules.nse.auth.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.UserOtpAction;
import com.ocean.afefe.entities.modules.nse.auth.models.UserOtpAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserOtpAuthRepository extends JpaRepository<UserOtpAuth, UUID> {
    Optional<UserOtpAuth> findTopByEmailAddressIgnoreCaseAndActionOrderByCreatedAtDesc(
            String emailAddress,
            UserOtpAction action
    );
}
