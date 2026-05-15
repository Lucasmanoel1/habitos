package com.lucasmanoel.habitos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableFeignClients
public class HabitosApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitosApplication.class, args);
	}
	@Bean
	CommandLineRunner printMongoUri(Environment env) {
		return args -> System.out.println(">>> MONGO URI: " +
				env.getProperty("spring.mongodb.uri"));
	}

}