package com.ocean.afefe.entities.modules.contents.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocean.afefe.entities.common.BaseUUIDEntity;
import com.ocean.afefe.entities.modules.auth.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCourseView extends BaseUUIDEntity {

    @ManyToOne(optional = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String viewedCourse;     // A list of com.ocean.afefe.entities.modules.contents.data.CourseViewData // LIMIT of 50
}
