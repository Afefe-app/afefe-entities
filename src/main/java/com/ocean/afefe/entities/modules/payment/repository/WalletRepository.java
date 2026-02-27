package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.payment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    Optional<Wallet> findFirstByUser(User user);
}
