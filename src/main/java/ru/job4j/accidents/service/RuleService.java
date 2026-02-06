package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository; // <-- Spring Data!

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository; // <-- Интерфейс!

    public List<Rule> findAll() {
        return (List<Rule>) ruleRepository.findAll(); // Приведение типа
    }

    public Set<Rule> findByIds(Set<Integer> ids) {
        return ruleRepository.findByIdIn(ids); // Метод из интерфейса!
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}