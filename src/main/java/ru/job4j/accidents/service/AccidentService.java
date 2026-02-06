package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentRepository;

    public Accident save(Accident accident) {
        return accidentRepository.save(accident);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }
}