package com.example.DataBase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "Developer", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
