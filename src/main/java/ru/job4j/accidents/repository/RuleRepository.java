package ru.job4j.accidents.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;

public interface RuleRepository extends ListCrudRepository<Rule, Integer> {
    Set<Rule> findByIdIn(Set<Integer> ids);
}