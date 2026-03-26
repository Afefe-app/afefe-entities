package com.ocean.afefe.entities.modules.helpcenter.repository;

import com.ocean.afefe.entities.modules.helpcenter.models.SupportTicket;
import com.ocean.afefe.entities.modules.helpcenter.models.SupportTicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupportTicketMessageRepository extends JpaRepository<SupportTicketMessage, UUID> {
    List<SupportTicketMessage> findAllByTicketOrderByCreatedAtAsc(SupportTicket ticket);
}
