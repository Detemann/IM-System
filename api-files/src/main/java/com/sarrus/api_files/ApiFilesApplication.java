package com.sarrus.api_files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ApiFilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFilesApplication.class, args);
	}

}
