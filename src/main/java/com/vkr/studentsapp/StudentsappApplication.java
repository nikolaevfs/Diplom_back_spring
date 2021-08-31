package com.vkr.studentsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class StudentsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentsappApplication.class, args);
	}
}
