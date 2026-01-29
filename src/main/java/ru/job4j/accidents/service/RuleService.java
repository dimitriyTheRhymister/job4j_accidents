package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleJdbcRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleJdbcRepository ruleRepository;

    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    public Set<Rule> findByIds(Set<Integer> ids) {
        return ruleRepository.findByIds(ids);
    }
}