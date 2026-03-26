package com.ocean.afefe.entities.modules.helpcenter.repository;

import com.ocean.afefe.entities.modules.helpcenter.models.HelpArticle;
import com.ocean.afefe.entities.modules.helpcenter.models.HelpArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HelpArticleRepository extends JpaRepository<HelpArticle, UUID> {
    List<HelpArticle> findAllByPublishedTrueOrderByCreatedAtDesc();
    List<HelpArticle> findAllByCategoryAndPublishedTrueOrderByCreatedAtDesc(HelpArticleCategory category);
}
