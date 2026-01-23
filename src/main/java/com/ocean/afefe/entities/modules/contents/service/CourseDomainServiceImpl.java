package com.ocean.afefe.entities.modules.contents.service;

import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.auth.models.User;
import com.ocean.afefe.entities.modules.contents.models.Course;
import com.ocean.afefe.entities.modules.contents.models.CourseVersion;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.ocean.afefe.entities.modules.contents.models.Lesson;
import com.ocean.afefe.entities.modules.contents.models.Module;
import com.ocean.afefe.entities.modules.contents.repository.CourseRepository;
import com.ocean.afefe.entities.modules.contents.repository.CourseVersionRepository;
import com.ocean.afefe.entities.modules.contents.repository.InstructorRepository;
import com.ocean.afefe.entities.modules.contents.repository.LessonRepository;
import com.ocean.afefe.entities.modules.contents.repository.ModuleRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.MessageUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseDomainServiceImpl implements CourseDomainService {

    private final MessageUtil messageUtil;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseVersionRepository courseVersionRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    private final static String DEFAULT_COURSE_VERSION = "DRAFT";

    @Override
    public void validateCourseTitleUniquenessForInstructor(String title, Organization organization, Instructor instructor){
        if(courseRepository.existsByTitleHashAndOwnerInstructorAndOrg(Course.buildTitleHash(title), instructor, organization)){
            throw HttpUtil.getResolvedException(ResponseCode.RECORD_ALREADY_EXIST, messageUtil.getMessage("title.course.for.instructor.in.organization.exists"));
        }
    }

    @Override
    public Instructor validateInstructorUserExistence(User user, Organization organization){
        return instructorRepository
                .findByUserAndOrg(user, organization)
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND, 
                        messageUtil.getMessage("instructor.record.not.found")));
    }

    @Override
    public Course validateCourseExistence(UUID courseId, Organization organization, Instructor instructor){
        return courseRepository.findById(courseId)
                .filter(course -> Objects.equals(course.getOrg(), organization) 
                        && Objects.equals(course.getOwnerInstructor(), instructor))
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND, 
                        messageUtil.getMessage("course.not.found")));
    }

    @Override
    public Course validateCourseExistenceById(UUID courseId, Organization organization){
        return courseRepository.findById(courseId)
                .filter(course -> Objects.equals(course.getOrg(), organization))
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND, 
                        messageUtil.getMessage("course.not.found")));
    }

    @Override
    public CourseVersion getOrCreateCourseVersion(Course course, User createdBy){
        Optional<CourseVersion> latestVersion = courseVersionRepository.findTopByCourseOrderByVersionNumDesc(course);
        if(latestVersion.isPresent()){
            return latestVersion.get();
        }
        CourseVersion courseVersion = CourseVersion.builder()
                .course(course)
                .versionNum(1)
                .status(DEFAULT_COURSE_VERSION)
                .createdBy(createdBy)
                .build();
        return courseVersionRepository.saveAndFlush(courseVersion);
    }

    @Override
    public Module validateModuleExistence(UUID moduleId, CourseVersion courseVersion){
        return moduleRepository.findById(moduleId)
                .filter(module -> Objects.equals(module.getCourseVersion(), courseVersion))
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND, 
                        messageUtil.getMessage("module.not.found")));
    }

    @Override
    public Lesson validateLessonExistence(UUID lessonId, Module module){
        return lessonRepository.findById(lessonId)
                .filter(lesson -> Objects.equals(lesson.getModule(), module))
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND, 
                        messageUtil.getMessage("lesson.not.found")));
    }

    @Override
    public Lesson validateLessonBelongsToInstructor(UUID lessonId, Organization organization, Instructor instructor){
        return lessonRepository.findById(lessonId)
                .filter(lesson -> {
                    Course course = lesson.getModule().getCourseVersion().getCourse();
                    return Objects.equals(course.getOwnerInstructor(), instructor) 
                            && Objects.equals(course.getOrg(), organization);
                })
                .orElseThrow(() -> HttpUtil.getResolvedException(
                        ResponseCode.RECORD_NOT_FOUND, 
                        messageUtil.getMessage("lesson.not.found")));
    }
}
