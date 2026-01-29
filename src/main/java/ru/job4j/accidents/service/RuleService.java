package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleMem ruleMem;

    public Collection<Rule> findAll() {
        return ruleMem.findAll();
    }

    public Optional<Rule> findById(int id) {
        return ruleMem.findById(id);
    }

    public Set<Rule> findByIds(Set<Integer> ids) {
        return ruleMem.findByIds(ids);
    }
}