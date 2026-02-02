package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleHibernate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleHibernate ruleRepository;

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Set<Rule> findByIds(Set<Integer> ids) {
        return ruleRepository.findByIds(ids);
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }
}