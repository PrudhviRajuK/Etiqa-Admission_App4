package com.university.student.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.university.student.exception.CustomErrorType;
import com.university.student.model.Student;
import com.university.student.service.CourseService;
import com.university.student.service.StudentService;



@RestController
@RequestMapping("/api")
public class StudentController {
	
	public static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;
    
    
    // -------------------Retrieve All Students ---------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> listAllStudents() {
    	List<Student> students = studentService.getAllStudents();
    	if (students.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }
    
    // -------------------Retrieve student by id------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/student/{student_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentById(@PathVariable(value = "student_id") Long student_id) {
    	
    	 logger.info("Fetching student with id {}", student_id);
    	 Student studentRetrieve = studentService.findById(student_id);
    	 
    	 if(studentRetrieve == null ) {
    		 logger.error("student with id {} not found.", student_id);
    	     return new ResponseEntity(new CustomErrorType("student with id " + student_id 
    	                    + " not found"), HttpStatus.NOT_FOUND);
    		 
    	 }
    	 return new ResponseEntity<Student>(studentRetrieve, HttpStatus.OK);
    }
    
    // -------------------add student-------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{course_id}/student", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudent(@PathVariable(value = "course_id") Long course_id, @RequestBody Student student,
    																						  UriComponentsBuilder ucBuilder) {
    	 logger.info("Creating Student : {}", student);
    	 
    	 if (studentService.isStudentExist(student)) {
             logger.error("Unable to create. A Student with name {} already exist", student.getStudent_name());
             return new ResponseEntity(new CustomErrorType("Unable to create. A Student with name " + 
            		 student.getStudent_name() + " already exist."),HttpStatus.CONFLICT);
         }
    	 
    	 studentService.createStudent(course_id, student);
    	 
    	 HttpHeaders headers = new HttpHeaders();
         headers.setLocation(ucBuilder.path("/api/student/{student_id}").buildAndExpand(student.getStudent_id()).toUri());
         return new ResponseEntity<String>(headers, HttpStatus.CREATED); 
    }
    
    // -------------------update student-------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/student/{student_id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@PathVariable(value = "student_id") Long student_id, @RequestBody Student student) {
    	logger.info("Updating student with id {}", student_id);
    	Student studentUpdate = studentService.findById(student_id);
    	
    	if (studentUpdate == null) {
            logger.error("Unable to update. student with id {} not found.", student_id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. student with id " + student_id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
    	
    	if (studentService.isStudentExist(student)) {
            logger.error("Unable to update. A Student with name {} already exist", student.getStudent_name());
            return new ResponseEntity(new CustomErrorType("Unable to update. A Student with name " + 
           		 student.getStudent_name() + " already exist."),HttpStatus.CONFLICT);
        }
    	studentUpdate = studentService.updateStudentById(student_id, student);
        
        return new ResponseEntity<Student>(studentUpdate, HttpStatus.OK);
    }
    
    // -------------------delete student-------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/student/{student_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudentById(@PathVariable(value = "student_id") long student_id) {
    	logger.info("Fetching & Deleting Student with id {}", student_id);
    	Student studentDelete = studentService.findById(student_id);
    	
    	if(studentDelete == null ) {
    		logger.error("Unable to delete. User with id {} not found.", student_id);
    		return new ResponseEntity(new CustomErrorType
    								 ("Unable to delete. User with id " + student_id + " not found."),HttpStatus.NOT_FOUND);
    	}
    	studentService.deleteStudentById(student_id);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }
    
}
