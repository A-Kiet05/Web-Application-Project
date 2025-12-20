package com.example.Web.Application.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplicationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplicationProjectApplication.class, args);
		System.out.println("Server running on http://localhost:8081");
	}

}
