package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);
    private final AtomicInteger nextTypeId = new AtomicInteger(1);
    private final AtomicInteger nextRuleId = new AtomicInteger(1);

    public AccidentMem() {
        // Инициализируем типы
        saveType(new AccidentType(0, "Две машины"));
        saveType(new AccidentType(0, "Машина и человек"));
        saveType(new AccidentType(0, "Машина и велосипед"));
        saveType(new AccidentType(0, "Одно транспортное средство"));

        // Инициализируем статьи нарушений
        saveRule(new Rule(0, "Статья. 1"));
        saveRule(new Rule(0, "Статья. 2"));
        saveRule(new Rule(0, "Статья. 3"));
        saveRule(new Rule(0, "Статья. 12.9 - Превышение скорости"));
        saveRule(new Rule(0, "Статья. 12.12 - Проезд на красный"));
        saveRule(new Rule(0, "Статья. 12.19 - Нарушение правил парковки"));

// Инициализируем инциденты с типами и правилами
        Accident accident1 = new Accident(0, "Нарушение ПДД",
                "Превышение скорости на 20 км/ч", "ул. Ленина, д. 10",
                findTypeById(1).orElse(null), new HashSet<>());
        accident1.setRules(Set.of(Objects.requireNonNull(findRuleById(4).orElse(null))));
        save(accident1);

        Accident accident2 = new Accident(0, "ДТП",
                "Столкновение двух автомобилей", "пр. Мира, д. 25",
                findTypeById(2).orElse(null), new HashSet<>());
        accident2.setRules(Set.of(Objects.requireNonNull(findRuleById(1).orElse(null)), Objects.requireNonNull(findRuleById(2).orElse(null))));
        save(accident2);

// НОВЫЕ ЗАПИСИ:

// Инцидент с велосипедистом (машина и велосипед)
        Accident accident3 = new Accident(0, "Наезд на велосипедиста",
                "Водитель не уступил дорогу велосипедисту на перекрестке", "ул. Спортивная, д. 15",
                findTypeById(3).orElse(null), new HashSet<>());
        accident3.setRules(Set.of(
                Objects.requireNonNull(findRuleById(3).orElse(null)),   // Статья. 3
                Objects.requireNonNull(findRuleById(5).orElse(null))    // Статья. 12.12 - Проезд на красный
        ));
        save(accident3);

// Инцидент с парковкой
        Accident accident4 = new Accident(0, "Незаконная парковка",
                "Парковка на газоне в жилой зоне", "пр. Победы, д. 42",
                findTypeById(4).orElse(null), new HashSet<>());  // Одно транспортное средство
        accident4.setRules(Set.of(
                Objects.requireNonNull(findRuleById(6).orElse(null))    // Статья. 12.19 - Нарушение правил парковки
        ));
        save(accident4);

// ДТП с пешеходом
        Accident accident5 = new Accident(0, "ДТП с пешеходом",
                "Наезд на пешехода на пешеходном переходе", "ул. Пушкина, д. 8",
                findTypeById(2).orElse(null), new HashSet<>());  // Машина и человек
        accident5.setRules(Set.of(
                Objects.requireNonNull(findRuleById(1).orElse(null)),   // Статья. 1
                Objects.requireNonNull(findRuleById(2).orElse(null)),   // Статья. 2
                Objects.requireNonNull(findRuleById(5).orElse(null))    // Статья. 12.12 - Проезд на красный
        ));
        save(accident5);

// Незначительное нарушение
        Accident accident6 = new Accident(0, "Негорящие фары",
                "Езда в темное время суток с негорящими фарами", "ул. Вечерняя, д. 33",
                findTypeById(1).orElse(null), new HashSet<>());  // Две машины (технически можно любой тип)
        accident6.setRules(Set.of(
                Objects.requireNonNull(findRuleById(4).orElse(null))    // Статья. 12.9 - Превышение скорости (или другая подходящая)
        ));
        save(accident6);
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

    // Методы для Rule
    public Rule saveRule(Rule rule) {
        if (rule.getId() == 0) {
            rule.setId(nextRuleId.getAndIncrement());
        }
        rules.put(rule.getId(), rule);
        return rule;
    }

    public Collection<Rule> findAllRules() {
        return rules.values();
    }

    public Optional<Rule> findRuleById(int id) {
        return Optional.ofNullable(rules.get(id));
    }

    public Set<Rule> findRulesByIds(Set<Integer> ids) {
        return ids.stream()
                .map(this::findRuleById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}