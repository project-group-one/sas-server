package com.food.sas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.food.sas.data.repository")
@SpringBootApplication
public class SasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SasApplication.class, args);
    }

}

