package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;

public interface RuleRepository extends CrudRepository<Rule, Integer> {
    Set<Rule> findByIdIn(Set<Integer> ids); // Автоматически сгенерирует IN запрос
}