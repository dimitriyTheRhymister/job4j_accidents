package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository; // <-- Оставляем

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentRepository;
    // УДАЛИ ЭТО - дублирование с AccidentTypeService и RuleService:
    // private final AccidentTypeRepository typeRepository;
    // private final RuleRepository ruleRepository;

    public Accident save(Accident accident) {
        return accidentRepository.save(accident);
    }

    public List<Accident> findAll() {
        return (List<Accident>) accidentRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    // УДАЛИ эти методы - они уже есть в AccidentTypeService и RuleService:
    // public List<AccidentType> findAllTypes() { ... }
    // public Set<Rule> findRulesByIds(Set<Integer> ids) { ... }
}