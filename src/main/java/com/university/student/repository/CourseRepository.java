package com.university.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.student.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
