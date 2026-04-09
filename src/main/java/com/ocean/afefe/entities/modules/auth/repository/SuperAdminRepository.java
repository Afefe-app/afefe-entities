package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.SuperAdmin;
import com.ocean.afefe.entities.modules.auth.models.SuperAdminStatus;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, UUID> {
    boolean existsByUser(User user);
    Optional<SuperAdmin> findByUser(User user);
    Optional<SuperAdmin> findByUser_Id(UUID userId);
    Page<SuperAdmin> findAllByUser_FullNameContainingIgnoreCaseOrUser_EmailAddressContainingIgnoreCase(String fullName, String emailAddress, Pageable pageable);
    Page<SuperAdmin> findAllByStatus(SuperAdminStatus status, Pageable pageable);
    long countByStatus(SuperAdminStatus status);
}
