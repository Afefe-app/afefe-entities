package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.SuperAdmin;
import com.ocean.afefe.entities.modules.auth.models.SuperAdminStatus;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, UUID> {
    boolean existsByUser(User user);
    Optional<SuperAdmin> findByUser(User user);
    Optional<SuperAdmin> findByUser_Id(UUID userId);

    @Query("""
            SELECT sa FROM SuperAdmin sa
            JOIN FETCH sa.user u
            WHERE sa.id = :id
            """)
    Optional<SuperAdmin> findByIdWithUser(@Param("id") UUID id);

    @Query(
            value = """
            SELECT sa FROM SuperAdmin sa
            JOIN FETCH sa.user u
            WHERE (:status IS NULL OR sa.status = :status)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%')))
            """,
            countQuery = """
            SELECT COUNT(sa) FROM SuperAdmin sa
            JOIN sa.user u
            WHERE (:status IS NULL OR sa.status = :status)
              AND (:search IS NULL OR :search = ''
                   OR LOWER(COALESCE(u.fullName, '')) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(COALESCE(u.emailAddress, '')) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<SuperAdmin> findAllFiltered(
            @Param("status") SuperAdminStatus status,
            @Param("search") String search,
            Pageable pageable);

    @Query("""
            SELECT sa FROM SuperAdmin sa
            JOIN FETCH sa.user u
            ORDER BY sa.grantedAt DESC
            """)
    List<SuperAdmin> findAllWithUser();

    Page<SuperAdmin> findAllByUser_FullNameContainingIgnoreCaseOrUser_EmailAddressContainingIgnoreCase(String fullName, String emailAddress, Pageable pageable);
    Page<SuperAdmin> findAllByStatus(SuperAdminStatus status, Pageable pageable);
    long countByStatus(SuperAdminStatus status);
}
