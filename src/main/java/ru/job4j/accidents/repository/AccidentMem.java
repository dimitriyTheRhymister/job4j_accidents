package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public AccidentMem() {
        // Теперь инциденты создаются через сервисы
        // Инициализацию переносим в сервисный слой
    }

    public Accident save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(nextId.getAndIncrement());
        }
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public boolean deleteById(int id) {
        return accidents.remove(id) != null;
    }

    public boolean update(Accident accident) {
        if (!accidents.containsKey(accident.getId())) {
            return false;
        }
        accidents.put(accident.getId(), accident);
        return true;
    }
}