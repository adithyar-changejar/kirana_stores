package com.example.kiranastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * The type Kiranastore application.
 */
@SpringBootApplication(
        exclude = {
                UserDetailsServiceAutoConfiguration.class
        }
)
public class KiranastoreApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(KiranastoreApplication.class, args);
    }
}
