package ru.job4j.accidents.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.List;

public interface AccidentRepository extends ListCrudRepository<Accident, Integer> {

    List<Accident> findByNameContaining(String name);
}