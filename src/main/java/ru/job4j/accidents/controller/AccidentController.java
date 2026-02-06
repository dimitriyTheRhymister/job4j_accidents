package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService; // <-- Используем!
    private final RuleService ruleService; // <-- Используем!

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll()); // <-- Через сервис
        model.addAttribute("rules", ruleService.findAll()); // <-- Через сервис
        model.addAttribute("accident", new Accident());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam("type.id") int typeId,
                       HttpServletRequest req) {
        // Используем AccidentTypeService
        accidentTypeService.findById(typeId).ifPresent(accident::setType);

        // Используем RuleService
        String[] ruleIds = req.getParameterValues("rIds");
        if (ruleIds != null) {
            Set<Integer> ids = Arrays.stream(ruleIds)
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
            accident.setRules(ruleService.findByIds(ids)); // <-- Через сервис
        }

        accidentService.save(accident);
        return "redirect:/";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        Optional<Accident> accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("accident", accidentOptional.get());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident,
                         @RequestParam("type.id") int typeId,
                         HttpServletRequest req) {
        // Устанавливаем тип через сервис
        accidentTypeService.findById(typeId).ifPresent(accident::setType);

        // Получаем выбранные статьи через сервис
        String[] ruleIds = req.getParameterValues("rIds");
        if (ruleIds != null) {
            Set<Integer> ids = Arrays.stream(ruleIds)
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
            accident.setRules(ruleService.findByIds(ids));
        }

        accidentService.save(accident); // Используем save для обновления
        return "redirect:/";
    }

    @GetMapping("/deleteAccident")
    public String delete(@RequestParam("id") int id) {
        accidentService.deleteById(id);
        return "redirect:/";
    }
}