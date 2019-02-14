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

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public Optional<Student> getStudentById(Long student_id) {
        if (!studentRepository.existsById(student_id)) {
            throw new ResourceNotFoundException("Student id ", "id", student_id);
        }
        return studentRepository.findById(student_id);
    }


    public Student createStudent(Long course_id, Student student) {
        Set<Student> students = new HashSet<>();
        Course course1 = new Course();

        Optional<Course> byId = courseRepository.findById(course_id);
        if (!byId.isPresent()) {
            throw new ResourceNotFoundException("Course id ", "id", course_id);
        }
        Course course = byId.get();

        //tie Author to Book
        student.setCourse(course);

        Student student1 = studentRepository.save(student);
        //tie Book to Author
        students.add(student1);
        course1.setStudent(students);

        return student1;

    }

    public Student updateStudentById(Long student_id, Student studentRequest) {
        if (!studentRepository.existsById(student_id)) {
            throw new ResourceNotFoundException("Student id ", "id", student_id);
        }
        Optional<Student> student = studentRepository.findById(student_id);

        if (!student.isPresent()) {
            throw new ResourceNotFoundException("Student id ", "id", student_id);
        }

        Student student1 = student.get();
        student1.setStudent_name(studentRequest.getStudent_name());
        student1.setStudent_age(studentRequest.getStudent_age());

        return studentRepository.save(student1);
    }

    public ResponseEntity<Object> deleteStudentById(long student_id) {
        if (!studentRepository.existsById(student_id)) {
            throw new ResourceNotFoundException("Student id ", "id", student_id);
        }

        studentRepository.deleteById(student_id);

        return ResponseEntity.ok().build();

    }


}
