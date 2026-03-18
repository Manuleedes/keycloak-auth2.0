package com.lidigu.auth_20;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Load .env file and expose every entry as a system property
		// so Spring Boot can resolve ${VAR} placeholders in application.yml
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing() // won't crash if .env is absent (e.g. in CI/CD)
				.load();

		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(Application.class, args);
	}

}
