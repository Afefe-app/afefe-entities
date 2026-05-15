package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmailAddress(String emailAddress);
    long countByUserType(UserType userType);

    /** B2B learner: platform learner with at least one organization membership where {@code joinedAt} is set. */
    @Query("""
            SELECT COUNT(DISTINCT u.id) FROM User u
            WHERE u.userType = :ut
              AND EXISTS (SELECT 1 FROM OrgMember om WHERE om.user = u AND om.joinedAt IS NOT NULL)
            """)
    long countLearnersWithJoinedOrganization(@Param("ut") UserType ut);

    /** B2C learner: platform learner with no joined organization membership. */
    @Query("""
            SELECT COUNT(DISTINCT u.id) FROM User u
            WHERE u.userType = :ut
              AND NOT EXISTS (SELECT 1 FROM OrgMember om WHERE om.user = u AND om.joinedAt IS NOT NULL)
            """)
    long countLearnersWithoutJoinedOrganization(@Param("ut") UserType ut);

    @Query("SELECT COUNT(u) FROM User u WHERE u.userType = :ut AND u.createdAt >= :start AND u.createdAt < :end")
    long countByUserTypeAndCreatedAtBetween(
            @Param("ut") UserType ut,
            @Param("start") Instant start,
            @Param("end") Instant end);
}
