package com.university.student.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.university.student.exception.ResourceNotFoundException;
import com.university.student.model.Course;
import com.university.student.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
    CourseRepository courseRepository;

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }


    public Optional<Course> getCourseById(Long course_id) {
        if (!courseRepository.existsById(course_id)) {
            throw new ResourceNotFoundException("Course Id ", "id", course_id);
        }
        return courseRepository.findById(course_id);
    }


    public Course createAuthor(Course course) {
        return courseRepository.save(course);

    }

    public Course updateCourseById(Long course_id, Course courseRequest) {
        if (!courseRepository.existsById(course_id)) {
            throw new ResourceNotFoundException("Course Id ", "id", course_id);
        }
        Optional<Course> course = courseRepository.findById(course_id);

        if (!course.isPresent()) {
            throw new ResourceNotFoundException("Course Id ", "id", course_id);
        }

        Course course1 = course.get();
        course1.setCourse_name(courseRequest.getCourse_name());
        return courseRepository.save(course1);

    }

    public ResponseEntity<Object> deleteCourseById(long course_id) {
        if (!courseRepository.existsById(course_id)) {
            throw new ResourceNotFoundException("Course Id ", "id", course_id);
        }

        courseRepository.deleteById(course_id);

        return ResponseEntity.ok().build();

    }


}
