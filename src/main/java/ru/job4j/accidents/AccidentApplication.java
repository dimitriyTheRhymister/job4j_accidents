package ru.job4j.accidents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccidentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccidentApplication.class, args);
        System.out.println("Приложение запущено на порту 8080!");
        System.out.println("Откройте: http://localhost:8080");
    }
}