package com.ocean.afefe.entities.modules.nse.events.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.Organization;
import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.ocean.afefe.entities.modules.nse.events.models.Event;
import com.ocean.afefe.entities.modules.nse.events.models.EventRegistration;
import com.ocean.afefe.entities.modules.nse.events.models.EventRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, UUID> {
    List<EventRegistration> findByUserAndOrgOrderByUpdatedAtDesc(User user, Organization org);
    List<EventRegistration> findByUserAndOrgAndStatusOrderByUpdatedAtDesc(
            User user, Organization org, EventRegistrationStatus status);
    Optional<EventRegistration> findFirstByUserAndEventOrderByUpdatedAtDesc(User user, Event event);
    Optional<EventRegistration> findByPaymentSessionReferenceAndUser_Id(String paymentSessionReference, UUID userId);
    long countByEvent_IdAndStatus(UUID eventId, EventRegistrationStatus status);
}
