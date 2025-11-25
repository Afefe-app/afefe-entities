package com.ocean.afefe.entities.modules.enrollments.repository;

import com.ocean.afefe.entities.modules.enrollments.models.CourseWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseWishListRepository extends JpaRepository<CourseWishList, UUID> {
}
