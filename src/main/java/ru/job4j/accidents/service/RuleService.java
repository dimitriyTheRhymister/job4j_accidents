package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleRepository ruleRepository;

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Set<Rule> findByIds(Set<Integer> ids) {
        return ruleRepository.findByIdIn(ids);
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}