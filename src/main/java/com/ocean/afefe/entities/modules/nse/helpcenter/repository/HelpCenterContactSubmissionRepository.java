package com.ocean.afefe.entities.modules.nse.helpcenter.repository;

import com.ocean.afefe.entities.modules.nse.helpcenter.models.HelpCenterContactSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HelpCenterContactSubmissionRepository extends JpaRepository<HelpCenterContactSubmission, UUID> {
}
