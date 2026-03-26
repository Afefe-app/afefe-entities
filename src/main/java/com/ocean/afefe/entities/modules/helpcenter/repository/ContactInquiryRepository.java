package com.ocean.afefe.entities.modules.helpcenter.repository;

import com.ocean.afefe.entities.modules.helpcenter.models.ContactInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactInquiryRepository extends JpaRepository<ContactInquiry, UUID> {
}
