package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.UserOtpAction;
import com.ocean.afefe.entities.modules.auth.models.UserOtpAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserOtpAuthRepository extends JpaRepository<UserOtpAuth, UUID> {

    UserOtpAuth findTopByEmailAddressAndActionOrderByCreatedAtDesc(String emailAddress, UserOtpAction action); // latest otp creared for that email and action
}
