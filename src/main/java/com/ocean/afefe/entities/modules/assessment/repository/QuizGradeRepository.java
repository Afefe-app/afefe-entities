package com.ocean.afefe.entities.modules.assessment.repository;

import com.ocean.afefe.entities.modules.assessment.model.QuizGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuizGradeRepository extends JpaRepository<QuizGrade, UUID> {

    @Query("""
            SELECT u.id AS userId, u.fullName AS fullName, u.avatarUrl AS avatarUrl,
                   COALESCE(SUM(g.totalPoints), 0.0) AS totalPoints
            FROM QuizGrade g
            JOIN g.attempt qa
            JOIN qa.user u
            WHERE u.userType = com.ocean.afefe.entities.modules.auth.models.UserType.PLATFORM_LEARNER
            GROUP BY u.id, u.fullName, u.avatarUrl
            ORDER BY COALESCE(SUM(g.totalPoints), 0.0) DESC
            """)
    Page<com.ocean.afefe.entities.modules.admin.projection.QuizPointsSummary> findTopLearnersByQuizPoints(Pageable pageable);
}
