package com.example.ELearningSys_BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ELearningBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ELearningBackEndApplication.class, args);
	}

	@RequestMapping
	public String home(){
		return "kavindu";
	}

}
