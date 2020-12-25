package com.revature.chronicle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class ChronicleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChronicleApplication.class, args);
	}

	@CrossOrigin
	@GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		
		System.out.println("IT worked");
		
		return String.format("Hello %s!", name);
	}
}