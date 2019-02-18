package com.university.student.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.university.student.exception.CustomErrorType;
import com.university.student.model.Course;
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
    @CrossOrigin
	@RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
    public ResponseEntity<List<StudentBean>> listAllStudents() {
    	List<Student> students = studentService.getAllStudents();
    	
    	List<StudentBean> studentBeanList = new ArrayList<>();
    
    	if(CollectionUtils.isEmpty(students)){
    		return new ResponseEntity<List<StudentBean>>(studentBeanList, HttpStatus.OK);
    	}

    	students.forEach(student ->{
    		StudentBean bean = new StudentBean();
    		
    		bean.setId(student.getStudent_id());
    		bean.setName(student.getStudent_name());
    		bean.setAge(student.getStudent_age());
    		bean.setCourseId(student.getCourse().getCourse_id()); 
    		bean.setCourse(student.getCourse().getCourse_name());
    		studentBeanList.add(bean);
    	});
        
    	return new ResponseEntity<List<StudentBean>>(studentBeanList, HttpStatus.OK);
    }
    
    // -------------------Retrieve student by id------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @CrossOrigin
	@RequestMapping(value = "/student/{student_id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudentById(@PathVariable(value = "student_id") Long student_id) {
    	
    	 logger.info("Fetching student with id {}", student_id);
    	 Student student = studentService.findById(student_id);
    	 
    	 if(student == null ) {
    		 logger.error("student with id {} not found.", student_id);
    	     return new ResponseEntity(new CustomErrorType("student with id " + student_id 
    	                    + " not found"), HttpStatus.NOT_FOUND);
    		 
    	 }
    	 
    	 StudentBean bean = new StudentBean();
    	 bean.setId(student.getStudent_id());
 		 bean.setName(student.getStudent_name());
 		 bean.setAge(student.getStudent_age());
         bean.setCourseId(student.getCourse().getCourse_id());  
         bean.setCourse(student.getCourse().getCourse_name()); 
         
    	 return new ResponseEntity<StudentBean>(bean, HttpStatus.OK);
    }
    
    // -------------------add student-------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @CrossOrigin
	@RequestMapping(value = "/{course_id}/student", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudent(@PathVariable(value = "course_id") Long course_id, @RequestBody Student student) {
    	 logger.info("Creating Student : {}", student);
    	 
    	 if (studentService.isStudentExist(student)) {
             logger.error("Unable to create. A Student with name {} already exist", student.getStudent_name());
             return new ResponseEntity(new CustomErrorType("Unable to create. A Student with name " + 
            		 student.getStudent_name() + " already exist."),HttpStatus.CONFLICT);
         }
    	 
    	Student student1 =  studentService.createStudent(course_id, student);
    	 
    	// HttpHeaders headers = new HttpHeaders();
         //headers.setLocation(ucBuilder.path("/api/student/{student_id}").buildAndExpand(student.getStudent_id()).toUri());
          return new ResponseEntity(student1,HttpStatus.CREATED);
    }
    
    // -------------------update student-------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @CrossOrigin
	@RequestMapping(value = "/student/{student_id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateStudent(@PathVariable(value = "student_id") Long student_id, @RequestBody StudentBean studentBean) {
    	logger.info("Updating student with id {}", student_id);
    	Student studentUpdate = studentService.findById(student_id);
    	
    	if (studentUpdate == null) {
            logger.error("Unable to update. student with id {} not found.", student_id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. student with id " + student_id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
    	
    	Student student = new Student();
    	
    	Optional<Course>  course = courseService.getCourseById(studentBean.getCourseId());
    	student.setStudent_id(studentBean.getId());
    	student.setStudent_name(studentBean.getName());
    	student.setStudent_age(studentBean.getAge());
    	student.setCourse(course.get());
    	studentUpdate = studentService.updateStudentById(student_id, student);
        
        return new ResponseEntity<Student>(studentUpdate, HttpStatus.OK);
    }
    
    // -------------------delete student-------------------------------------------
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @CrossOrigin
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
    
    //
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/students/{course_id}", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> listOfStudentsByCourseID(@PathVariable(value = "course_id") Long course_id) {
    	 logger.info("Fetching student with id {}", course_id);
    	 
    	 List<Student> studentRetrieve = studentService.listOfStudentsByCourseID(course_id);
    	 
    	 if(studentRetrieve == null ) {
    		 logger.error("student with id {} not found.", course_id);
    	     return new ResponseEntity(new CustomErrorType("student with id " + course_id 
    	                    + " not found"), HttpStatus.NOT_FOUND);
    	}
    	 
    	 return new ResponseEntity<List<Student>>(studentRetrieve, HttpStatus.OK);
    }
   
   
    public static class StudentBean {
    	
    	private Long id;
    	private String name;
    	private Integer age;
    	private String course;
    	private Long courseId;
    	
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public String getCourse() {
			return course;
		}
		public void setCourse(String course) {
			this.course = course;
		}
		public Long getCourseId() {
			return courseId;
		}
		public void setCourseId(Long courseId) {
			this.courseId = courseId;
		}	
		
		
    }
    
}
