package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public AccidentMem() {
        save(new Accident(0, "Нарушение ПДД",
                "Превышение скорости на 20 км/ч", "ул. Ленина, д. 10"));
        save(new Accident(0, "ДТП",
                "Столкновение двух автомобилей", "пр. Мира, д. 25"));
        save(new Accident(0, "Парковка",
                "Парковка в неположенном месте", "ул. Центральная, д. 5"));
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
}