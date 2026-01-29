package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeJdbcRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeJdbcRepository accidentTypeRepository;

    public Collection<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}