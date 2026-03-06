package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.payment.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
}
