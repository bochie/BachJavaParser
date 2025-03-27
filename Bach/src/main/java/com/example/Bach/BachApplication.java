package com.example.Bach;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScheduling // Включає розклади завдань
public class BachApplication {
	public static void main(String[] args) {
		SpringApplication.run(BachApplication.class, args);
	}
}
