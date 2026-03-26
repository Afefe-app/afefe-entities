package com.ocean.afefe.entities.modules.helpcenter.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.helpcenter.models.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, UUID> {
    List<SupportTicket> findAllBySubmittedByOrderByCreatedAtDesc(User submittedBy);
    Optional<SupportTicket> findByIdAndSubmittedBy(UUID id, User submittedBy);
}
