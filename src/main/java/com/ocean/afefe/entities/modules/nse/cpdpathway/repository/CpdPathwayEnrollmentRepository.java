package com.ocean.afefe.entities.modules.nse.cpdpathway.repository;

import com.ocean.afefe.entities.modules.nse.auth.models.Organization;
import com.ocean.afefe.entities.modules.nse.auth.models.User;
import com.ocean.afefe.entities.modules.nse.cpdpathway.models.CpdPathway;
import com.ocean.afefe.entities.modules.nse.cpdpathway.models.CpdPathwayEnrollment;
import com.ocean.afefe.entities.modules.nse.cpdpathway.models.CpdPathwayEnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CpdPathwayEnrollmentRepository extends JpaRepository<CpdPathwayEnrollment, UUID> {
    List<CpdPathwayEnrollment> findByUserAndOrgOrderByUpdatedAtDesc(User user, Organization org);
    List<CpdPathwayEnrollment> findByUserAndOrgAndStatusOrderByUpdatedAtDesc(
            User user, Organization org, CpdPathwayEnrollmentStatus status);
    Optional<CpdPathwayEnrollment> findFirstByUserAndPathwayOrderByUpdatedAtDesc(User user, CpdPathway pathway);
    Optional<CpdPathwayEnrollment> findByPaymentSessionReferenceAndUser_Id(String paymentSessionReference, UUID userId);
}
