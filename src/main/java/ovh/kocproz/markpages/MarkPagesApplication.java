package ovh.kocproz.markpages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@PropertySource("application.properties")
@SpringBootApplication
@EnableScheduling
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
