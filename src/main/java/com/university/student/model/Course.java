package com.university.student.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "course")
@JsonIgnoreProperties
public class Course {
	
	@Id
	@Column(name="course_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long course_id;
	
    @NotBlank
    @Column(name="course_name")
	private String course_name;
    
    @OneToMany(mappedBy = "course")
	private Set<Student> student = new HashSet<>();
    
	public Course() {
			
	}

	public Set<Student> getStudent() {
		return student;
	}
	

	public void setStudent(Set<Student> student) {
		this.student = student;
	}
    
	public Long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}


	public String getCourse_name() {
		return course_name;
	}


	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
}
