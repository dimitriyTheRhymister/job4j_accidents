package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccidentService {
    private final AccidentMem accidentMem;

    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;
    }

    public Collection<Accident> getAllAccidents() {
        return accidentMem.findAll();
    }

    public Accident save(Accident accident) {
        return accidentMem.save(accident);
    }

    public Accident update(Accident accident) {
        return accidentMem.save(accident); // или отдельный метод update
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public boolean deleteById(int id) {
        return accidentMem.deleteById(id);
    }
}