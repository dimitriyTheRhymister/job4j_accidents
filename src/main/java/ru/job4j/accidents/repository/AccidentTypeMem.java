package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem {
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public AccidentTypeMem() {
        save(new AccidentType(0, "Две машины"));
        save(new AccidentType(0, "Машина и человек"));
        save(new AccidentType(0, "Машина и велосипед"));
        save(new AccidentType(0, "Одно транспортное средство"));
    }

    public AccidentType save(AccidentType type) {
        if (type.getId() == 0) {
            type.setId(nextId.getAndIncrement());
        }
        types.put(type.getId(), type);
        return type;
    }

    public Collection<AccidentType> findAll() {
        return types.values();
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }
}