package me.matrix89.markpages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@SpringBootApplication
public class MarkPagesApplication {

	@Bean
	public Logger logger() {
		return LoggerFactory.getLogger(MarkPagesApplication.class.getName());
	}

	@Bean
	public Pbkdf2PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(MarkPagesApplication.class, args);
	}
}
