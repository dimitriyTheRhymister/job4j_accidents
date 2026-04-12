package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

@Controller
@AllArgsConstructor
public class RegController {

    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        // Кодируем пароль
        user.setPassword(encoder.encode(user.getPassword()));
        // Активируем пользователя
        user.setEnabled(true);
        // Назначаем роль USER по умолчанию
        Authority userRole = authorities.findByAuthority("ROLE_USER");
        user.setAuthority(userRole);
        // Сохраняем в БД
        users.save(user);
        // Перенаправляем на страницу входа
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}