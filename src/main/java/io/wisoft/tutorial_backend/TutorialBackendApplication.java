package io.wisoft.tutorial_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TutorialBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutorialBackendApplication.class, args);
    }

}
