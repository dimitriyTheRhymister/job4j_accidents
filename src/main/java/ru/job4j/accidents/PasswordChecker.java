package ru.job4j.accidents;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordChecker {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Генерируем новый хэш для "secret"
        String newHash = encoder.encode("secret");
        System.out.println("Новый хэш для 'secret': " + newHash);

        // Проверяем старый хэш
        String oldHash = "$2a$10$wY1twJhMQjGVxv4y5dBC5ucCBlzkzT4FIGa4FNB/pS9GaXC2wm9/W";
        boolean matches = encoder.matches("secret", oldHash);
        System.out.println("Старый хэш подходит для 'secret': " + matches);
    }
}