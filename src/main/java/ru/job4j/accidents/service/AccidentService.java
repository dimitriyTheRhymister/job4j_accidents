package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentHibernate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentHibernate accidentRepository;
    private final AccidentTypeService accidentTypeService;  // <-- Используем сервисы!
    private final RuleService ruleService;                  // <-- Используем сервисы!

    // ТОЛЬКО методы для Accident
    public Accident save(Accident accident) {
        return accidentRepository.save(accident);
    }

    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public boolean deleteById(int id) {
        return accidentRepository.deleteById(id);
    }

    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    // УДАЛИ все методы для Type и Rule!
    // Вместо них используй accidentTypeService и ruleService
}