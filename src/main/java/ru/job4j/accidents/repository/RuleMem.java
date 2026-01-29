package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class RuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public RuleMem() {
        save(new Rule(0, "Статья. 1"));
        save(new Rule(0, "Статья. 2"));
        save(new Rule(0, "Статья. 3"));
        save(new Rule(0, "Статья. 12.9 - Превышение скорости"));
        save(new Rule(0, "Статья. 12.12 - Проезд на красный"));
        save(new Rule(0, "Статья. 12.19 - Нарушение правил парковки"));
    }

    public Rule save(Rule rule) {
        if (rule.getId() == 0) {
            rule.setId(nextId.getAndIncrement());
        }
        rules.put(rule.getId(), rule);
        return rule;
    }

    public Collection<Rule> findAll() {
        return rules.values();
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    // Или более эффективно с Stream API:
    public Set<Rule> findByIds(Set<Integer> ids) {
        return ids.stream()
                .map(rules::get) // O(1) операция для HashMap
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}