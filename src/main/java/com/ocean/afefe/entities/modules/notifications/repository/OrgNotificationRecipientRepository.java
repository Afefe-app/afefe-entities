package com.ocean.afefe.entities.modules.notifications.repository;

import com.ocean.afefe.entities.modules.notifications.model.OrgNotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrgNotificationRecipientRepository extends JpaRepository<OrgNotificationRecipient, UUID> {

    List<OrgNotificationRecipient> findByOrgNotification_IdIn(Collection<UUID> notificationIds);

    List<OrgNotificationRecipient> findByOrgNotification_IdOrderByCreatedAtAsc(UUID orgNotificationId);
}
