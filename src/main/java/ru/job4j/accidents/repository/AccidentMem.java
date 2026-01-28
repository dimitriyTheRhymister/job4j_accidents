package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final AtomicInteger nextTypeId = new AtomicInteger(1);

    public AccidentMem() {
        // Инициализируем типы
        saveType(new AccidentType(0, "Две машины"));
        saveType(new AccidentType(0, "Машина и человек"));
        saveType(new AccidentType(0, "Машина и велосипед"));
        saveType(new AccidentType(0, "Одно транспортное средство"));
        saveType(new AccidentType(0, "Прочее"));

        // Инициализируем инциденты с типами
        save(new Accident(0, "Нарушение ПДД",
                "Превышение скорости на 20 км/ч", "ул. Ленина, д. 10",
                findTypeById(1).orElse(null)));
        save(new Accident(0, "ДТП",
                "Столкновение двух автомобилей", "пр. Мира, д. 25",
                findTypeById(2).orElse(null)));
        save(new Accident(0, "Парковка",
                "Парковка в неположенном месте", "ул. Центральная, д. 5",
                findTypeById(3).orElse(null)));
    }

    // Методы для Accident
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

    // Методы для AccidentType
    public AccidentType saveType(AccidentType type) {
        if (type.getId() == 0) {
            type.setId(nextTypeId.getAndIncrement());
        }
        types.put(type.getId(), type);
        return type;
    }

    public Collection<AccidentType> findAllTypes() {
        return types.values();
    }

    public Optional<AccidentType> findTypeById(int id) {
        return Optional.ofNullable(types.get(id));
    }
}