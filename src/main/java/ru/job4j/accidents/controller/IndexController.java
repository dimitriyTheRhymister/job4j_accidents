package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Добро пожаловать в приложение Автонарушители!");
        model.addAttribute("javaVersion", System.getProperty("java.version"));
        model.addAttribute("springBootVersion", "3.2.0");
        return "index";
    }

    @GetMapping("/test")
    @ResponseBody  // Эта аннотация говорит Spring возвращать текст, а не искать шаблон
    public String test() {
        return "Тестовая страница работает! Spring Boot 3 + Java 21";
    }

    // Новый JSON API endpoint
    @GetMapping("/api/hello")
    @ResponseBody  // Эта аннотация говорит Spring возвращать текст, а не искать шаблон
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, World!");
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
}