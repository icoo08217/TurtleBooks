package com.turtle.turtlebooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TurtlebooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurtlebooksApplication.class, args);
    }

}
