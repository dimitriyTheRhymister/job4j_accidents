package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentJdbcRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentJdbcRepository accidentRepository;

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
}