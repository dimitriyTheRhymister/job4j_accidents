package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository; // <-- Spring Data!

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeRepository accidentTypeRepository; // <-- Интерфейс!

    public List<AccidentType> findAll() {
        return (List<AccidentType>) accidentTypeRepository.findAll(); // Приведение типа
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}