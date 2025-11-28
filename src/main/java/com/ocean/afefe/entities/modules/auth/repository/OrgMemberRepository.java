package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrgMemberRepository extends JpaRepository<OrgMember, UUID> {
}
