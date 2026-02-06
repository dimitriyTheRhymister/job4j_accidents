package ru.job4j.accidents.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.accidents.model.AccidentType;

public interface AccidentTypeRepository extends ListCrudRepository<AccidentType, Integer> {
}