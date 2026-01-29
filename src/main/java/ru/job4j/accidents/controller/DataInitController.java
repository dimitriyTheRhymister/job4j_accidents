package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.*;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
public class DataInitController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @PostConstruct
    public void initData() {
        // Создаем тестовые данные если их нет
        if (accidentService.findAll().isEmpty()) {
            createTestData();
        }
    }

    private void createTestData() {
        // 1. Нарушение ПДД
        Accident accident1 = new Accident(0, "Нарушение ПДД",
                "Превышение скорости на 20 км/ч", "ул. Ленина, д. 10",
                findTypeById(1), new HashSet<>());
        accident1.setRules(Set.of(findRuleById(4)));
        accidentService.save(accident1);

        // 2. ДТП
        Accident accident2 = new Accident(0, "ДТП",
                "Столкновение двух автомобилей", "пр. Мира, д. 25",
                findTypeById(2), new HashSet<>());
        accident2.setRules(Set.of(findRuleById(1), findRuleById(2)));
        accidentService.save(accident2);

        // 3. Наезд на велосипедиста
        Accident accident3 = new Accident(0, "Наезд на велосипедиста",
                "Водитель не уступил дорогу велосипедисту", "ул. Спортивная, д. 15",
                findTypeById(3), new HashSet<>());
        accident3.setRules(Set.of(findRuleById(3), findRuleById(5)));
        accidentService.save(accident3);
    }

    private AccidentType findTypeById(int id) {
        return accidentTypeService.findById(id).orElse(null);
    }

    private Rule findRuleById(int id) {
        return ruleService.findById(id).orElse(null);
    }
}