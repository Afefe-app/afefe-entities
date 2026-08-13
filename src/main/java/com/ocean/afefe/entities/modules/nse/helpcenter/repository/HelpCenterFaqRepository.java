package com.ocean.afefe.entities.modules.nse.helpcenter.repository;

import com.ocean.afefe.entities.modules.nse.helpcenter.models.HelpCenterFaq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HelpCenterFaqRepository extends JpaRepository<HelpCenterFaq, UUID> {

    List<HelpCenterFaq> findByOrg_IdAndActiveTrueOrderByDisplayOrderAsc(UUID orgId);
}
