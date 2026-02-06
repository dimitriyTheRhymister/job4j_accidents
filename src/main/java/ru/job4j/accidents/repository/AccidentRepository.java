package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentRepository extends CrudRepository<Accident, Integer> {
    // Базовые методы уже есть: save(), findById(), findAll(), deleteById()

    // Можно добавить свои методы:
    List<Accident> findByNameContaining(String name); // Автоматически сгенерирует запрос!
}