package com.starwars.resistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ResistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResistenceApplication.class, args);
	}

}
