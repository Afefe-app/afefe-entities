package com.ocean.afefe.entities.modules.appuser.repository;

import com.ocean.afefe.entities.modules.appuser.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser> {

    AppUser findFirstByChannelId(String channelId);
    AppUser findFirstByChannel(String channel);
}
