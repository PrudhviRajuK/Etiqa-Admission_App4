package com.university.student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.university.student.model.Course;
import com.university.student.service.CourseService;

@RestController
public class CourseController {
	
	@Autowired
    CourseService courseService;
	
	@RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @RequestMapping(value = "/course", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Course createCourse(@RequestBody Course course) {
        return courseService.createAuthor(course);
    }

    @RequestMapping(value = "/course/{course_id}", method = RequestMethod.GET)
    public Optional<Course> getCourseById(@PathVariable(value = "course_id") Long course_id) {
        return courseService.getCourseById(course_id);
    }

    @RequestMapping(value = "/course", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Course updateCourse(@PathVariable(value = "course_id") Long course_id, @RequestBody Course course) {
        return courseService.updateCourseById(course_id, course);
    }

    @RequestMapping(value = "/course/{course_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCourseById(@PathVariable(value = "course_id") long course_id) {
        return courseService.deleteCourseById(course_id);
    }

}
