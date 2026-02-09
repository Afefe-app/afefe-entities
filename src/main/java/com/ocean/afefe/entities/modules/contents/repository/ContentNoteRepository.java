package com.ocean.afefe.entities.modules.contents.repository;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.ContentNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContentNoteRepository extends JpaRepository<ContentNote, UUID> {

    Page<ContentNote> findAllByUserAndOrg(User user, Organization org, Pageable pageable);

    Optional<ContentNote> findByIdAndUser(UUID id, User user);
}
