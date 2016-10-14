package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringcloudEurekaApplication {

	@RequestMapping("/index")
	public Object index() {
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudEurekaApplication.class, args);
	}
}
