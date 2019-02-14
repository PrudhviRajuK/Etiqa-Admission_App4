package com.university.student.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.university.student.model.Course;
import com.university.student.model.Student;
import com.university.student.service.CourseService;
import com.university.student.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    // get student
    @RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }
    
    // get student by id
    @RequestMapping(value = "/student/{student_id}", method = RequestMethod.GET)
    public Optional<Student> getStudentById(@PathVariable(value = "student_id") Long student_id) {
        return studentService.getStudentById(student_id);
    }

    // add student
    @RequestMapping(value = "/{course_id}/student", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Student createStudent(@PathVariable(value = "course_id") Long course_id, @RequestBody Student student) {
        return studentService.createStudent(course_id, student);
    }
    
    //update student
    @RequestMapping(value = "/student/{student_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Student updateBook(@PathVariable(value = "student_id") Long student_id, @RequestBody Student student) {
        return studentService.updateStudentById(student_id, student);
    }
    
    // delete student
    @RequestMapping(value = "/student/{student_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentById(@PathVariable(value = "student_id") long student_id) {
    	
    	Student student = studentService.findById(student_id);
    	
    	if(student.getStudent_id() != null ) {
    		 studentService.deleteStudentById(student_id);
    	}
          
    	
    	
    	return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
    
    
    /*@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
       // logger.info("Fetching & Deleting User with id {}", id);
 
        User user = userService.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
       
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }*/
    
}
