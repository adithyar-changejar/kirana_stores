package com.example.kiranastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class KiranastoreApplication {

    private final Environment env;

    public KiranastoreApplication(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void dump() {
        System.out.println(">>> spring.data.mongodb.uri = " + env.getProperty("spring.data.mongodb.uri"));
        System.out.println(">>> spring.data.mongodb.database = " + env.getProperty("spring.data.mongodb.database"));
    }

    public static void main(String[] args) {
        SpringApplication.run(KiranastoreApplication.class, args);
    }
}
