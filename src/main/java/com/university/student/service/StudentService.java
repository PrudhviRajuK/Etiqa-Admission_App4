package com.university.student.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.university.student.exception.ResourceNotFoundException;
import com.university.student.model.Course;
import com.university.student.model.Student;
import com.university.student.repository.CourseRepository;
import com.university.student.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;
    
    // get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    //create student
    public Student createStudent(Long course_id, Student student) {
       
    	Set<Student> students = new HashSet<>();
        Course course1 = new Course();

        Optional<Course> byId = courseRepository.findById(course_id);
        if (!byId.isPresent()) {
            throw new ResourceNotFoundException("Course id ", "id", course_id);
        }
        
        Course course = byId.get();
        student.setCourse(course);
        
        Student student1 = studentRepository.save(student);
        students.add(student1);
        course1.setStudent(students);

        return student1;

    }
    
    //update student
    public Student updateStudentById(Long student_id, Student studentRequest) {
       
        Optional<Student> student = studentRepository.findById(student_id);

        if (!student.isPresent()) {
            throw new ResourceNotFoundException("Student id ", "id", student_id);
        }
        
        Student student1 = student.get();
        student1.setStudent_name(studentRequest.getStudent_name());
        student1.setStudent_age(studentRequest.getStudent_age());
        student1.setCourse(studentRequest.getCourse());
        return studentRepository.save(student1);
    }
    
    //delete by id
    public ResponseEntity<Object> deleteStudentById(long student_id) {
        
    	if (!studentRepository.existsById(student_id)) {
            throw new ResourceNotFoundException("Student id ", "id", student_id);
        }
        studentRepository.deleteById(student_id);

        return ResponseEntity.ok().build();

    }
    
    //find by id
    public Student findById(long id) {
    	List<Student> stdList = getAllStudents();
		for(Student student : stdList){
			if(student.getStudent_id() == id){
				return student;
			}
		}
		return null;
	}
    
   //find by name
    public Student findByName(String name) {
    	List<Student> studentList = getAllStudents();
    	for(Student student : studentList){
			if(student.getStudent_name().equalsIgnoreCase(name)){
				return student;
			}
		}
		return null;
	}
    
    public boolean isStudentExist(Student student) {
		return findByName(student.getStudent_name())!=null;
	}
    
    
    
//students by course id
    
    public  List<Student> listOfStudentsByCourseID(long id) {
    	
    	List<Student> studentList = getAllStudents();
    	
    	List<Student> studentListTemp = new ArrayList<Student>();
    	
    	for(Student student : studentList){
    		
			if(student.getCourse().getCourse_id() == id){
				
				studentListTemp.add(student);
			}
			System.out.println("-------------------------"+studentListTemp.size()); 
			//return studentListTemp;
		}
    	 return studentListTemp;
    	//return null;
	}
    
}
