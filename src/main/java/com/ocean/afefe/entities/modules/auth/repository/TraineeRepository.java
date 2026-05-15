package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.Trainee;
import com.ocean.afefe.entities.modules.auth.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, UUID>, JpaSpecificationExecutor<Trainee> {

    Optional<Trainee> findByUserAndOrganization(User user, Organization organization);

    Optional<Trainee> findByUser_IdAndOrganization_Id(UUID userId, UUID organizationId);

    Page<Trainee> findAllByOrganization_Id(UUID organizationId, Pageable pageable);

    long countByOrganization_Id(UUID organizationId);

    @Query("""
            SELECT tr.organization.id, COUNT(tr)
            FROM Trainee tr
            WHERE tr.organization.id IN :orgIds
            GROUP BY tr.organization.id
            """)
    List<Object[]> countTraineesGroupedByOrgIds(@Param("orgIds") Collection<UUID> orgIds);

    @Query("SELECT COUNT(t) FROM Trainee t WHERE t.organization.id <> :excludedOrgId")
    long countTraineesInOrganizationsExcept(@Param("excludedOrgId") UUID excludedOrgId);

    @Query("""
            SELECT COUNT(t)
            FROM Trainee t
            WHERE t.organization.id <> :excludedOrgId
              AND t.createdAt >= :start
              AND t.createdAt < :end
            """)
    long countTraineesInOrganizationsExceptCreatedBetween(
            @Param("excludedOrgId") UUID excludedOrgId,
            @Param("start") Instant start,
            @Param("end") Instant end);
}
