package com.university.student.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties
@Table(name = "student")
public class Student {

	@Id
	@Column(name="student_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long student_id;
	
    @NotBlank
    @Column(name="student_name")
	private String student_name;
    
    
    @Column(name="student_age")
	private Integer student_age;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Course course;
    
    public Student() {
    	
    }

	public Long getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Long student_id) {
		this.student_id = student_id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public int getStudent_age() {
		return student_age;
	}

	public void setStudent_age(int student_age) {
		this.student_age = student_age;
	}

	@JsonIgnore
	public Course getCourse() {
		return course;
	}
	@JsonIgnore
	public void setCourse(Course course) {
		this.course = course;
	}
	
}
