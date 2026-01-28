package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    public Accident save(Accident accident) {
        return accidentMem.save(accident);
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public boolean deleteById(int id) {
        return accidentMem.deleteById(id);
    }

    public boolean update(Accident accident) {
        return accidentMem.update(accident);
    }

    public List<AccidentType> findAllTypes() {
        return accidentMem.findAllTypes().stream().toList();
    }

    public Optional<AccidentType> findTypeById(int id) {
        return accidentMem.findTypeById(id);
    }
}