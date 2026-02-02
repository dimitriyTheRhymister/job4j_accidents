package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeHibernate;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeHibernate accidentTypeRepository;

    public List<AccidentType> findAll() {
        return accidentTypeRepository.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return accidentTypeRepository.findById(id);
    }
}