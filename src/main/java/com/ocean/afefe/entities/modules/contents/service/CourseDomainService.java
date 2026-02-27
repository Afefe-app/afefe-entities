package com.ocean.afefe.entities.modules.contents.service;

import com.ocean.afefe.entities.modules.assessment.model.Quiz;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Module;
import com.ocean.afefe.entities.modules.contents.models.*;

import java.util.UUID;

public interface CourseDomainService {
    void validateCourseTitleUniquenessForInstructor(String title, Organization organization, Instructor instructor);

    Instructor validateInstructorUserExistence(User user, Organization organization);
    
    Course validateCourseExistence(UUID courseId, Organization organization, Instructor instructor);
    
    Course validateCourseExistenceById(UUID courseId, Organization organization);
    
    /**
     * Validates that the lesson asset exists and belongs to the given course (for learner progress).
     */
    LessonAsset validateLessonAssetBelongsToCourse(UUID lessonAssetId, UUID courseId, Organization organization);
    
    CourseVersion getOrCreateCourseVersion(Course course, User createdBy);
    
    Module validateModuleExistence(UUID moduleId, CourseVersion courseVersion);
    
    Module validateModuleBelongsToInstructor(UUID moduleId, Organization organization, Instructor instructor);
    
    Lesson validateLessonExistence(UUID lessonId, Module module);
    
    Lesson validateLessonBelongsToInstructor(UUID lessonId, Organization organization, Instructor instructor);

    LessonContent validateLessonContentBelongsToInstructor(UUID lessonContentId, Organization organization, Instructor instructor);

    LessonAsset validateLessonAssetExistsForLessonContent(UUID lessonAssetId, LessonContent lessonContent);

    Quiz validateQuizExistence(UUID quizId);
}
