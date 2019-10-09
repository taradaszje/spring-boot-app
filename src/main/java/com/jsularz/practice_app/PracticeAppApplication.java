package com.jsularz.practice_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PracticeAppApplication {

    //private static final Logger log = LoggerFactory.getLogger(PracticeAppApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(PracticeAppApplication.class);
    }
}
