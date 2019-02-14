package com.university.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.university.student.controller.StudentController;

@SpringBootApplication(scanBasePackages="com")
//@ComponentScan(basePackageClasses = StudentCourseController.class)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

