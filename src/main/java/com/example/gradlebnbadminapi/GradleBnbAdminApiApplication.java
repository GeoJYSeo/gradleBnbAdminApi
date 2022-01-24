package com.example.gradlebnbadminapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GradleBnbAdminApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradleBnbAdminApiApplication.class, args);
	}

}
