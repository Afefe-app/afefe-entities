package com.ocean.afefe.entities.modules.auth.repository;

import com.ocean.afefe.entities.modules.auth.models.WaitList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaitListRepository extends JpaRepository<WaitList, Long> {

    @Query("select w from WaitList w where w.email = :email")
    Optional<WaitList> findByEmail(@Param("email") String email);
}
