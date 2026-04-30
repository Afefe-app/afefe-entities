package com.ocean.afefe.entities.modules.helpcenter.repository;

import com.ocean.afefe.entities.modules.helpcenter.models.InquiryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InquiryRequestRepository extends JpaRepository<InquiryRequest, UUID> {
}
