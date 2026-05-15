package com.ocean.afefe.entities.modules.admin.projection;

import java.util.UUID;

/**
 * Spring Data closed projection for quiz points leaderboard queries.
 */
public interface QuizPointsSummary {
    UUID getUserId();

    String getFullName();

    String getAvatarUrl();

    Double getTotalPoints();
}
