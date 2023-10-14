package ru.bikkul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Hello world!
 */
@EnableAsync
@SpringBootApplication
public class MainParserApp {
    public static void main(String[] args) {
        SpringApplication.run(MainParserApp.class);
    }
}
