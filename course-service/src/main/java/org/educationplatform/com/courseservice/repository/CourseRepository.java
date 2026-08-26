package org.educationplatform.com.courseservice.repository;

import org.educationplatform.com.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
