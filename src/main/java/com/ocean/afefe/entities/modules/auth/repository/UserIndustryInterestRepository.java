package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.auth.models.UserIndustryInterest;
import com.ocean.afefe.entities.modules.taxonomy.models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserIndustryInterestRepository extends JpaRepository<UserIndustryInterest, UUID> {

    @Query("select ui from UserIndustryInterest ui where ui.user = :user")
    List<UserIndustryInterest> findAllByUser(@Param("user") User user);

    @Query("select ui from UserIndustryInterest ui where ui.user = :user and ui.industry not in :industries")
    List<UserIndustryInterest> findAllByUserAndIndustryNotIn(@Param("user") User user, @Param("industries") List<Industry> industries);

    @Query("select ui from UserIndustryInterest ui where ui.user = :user and ui.industry in :industries")
    List<UserIndustryInterest> findAllByUserAndIndustryIn(@Param("user") User user, @Param("industries") List<Industry> industries);
}
