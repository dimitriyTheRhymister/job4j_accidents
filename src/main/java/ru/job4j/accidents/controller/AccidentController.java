package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService; // <-- новый сервис
    private final RuleService ruleService; // <-- новый сервис

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("accident", new Accident());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam("type.id") int typeId,
                       HttpServletRequest req) {
        // Устанавливаем тип через новый сервис
        accidentTypeService.findById(typeId).ifPresent(accident::setType);

        // Получаем выбранные статьи через новый сервис
        String[] ruleIds = req.getParameterValues("rIds");
        if (ruleIds != null) {
            Set<Integer> ids = new HashSet<>();
            for (String id : ruleIds) {
                ids.add(Integer.parseInt(id));
            }
            accident.setRules(ruleService.findByIds(ids));
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
        // Устанавливаем тип через новый сервис
        accidentTypeService.findById(typeId).ifPresent(accident::setType);

        // Получаем выбранные статьи через новый сервис
        String[] ruleIds = req.getParameterValues("rIds");
        if (ruleIds != null) {
            Set<Integer> ids = new HashSet<>();
            for (String id : ruleIds) {
                ids.add(Integer.parseInt(id));
            }
            accident.setRules(ruleService.findByIds(ids));
        }

        accidentService.update(accident);
        return "redirect:/";
    }

    @GetMapping("/deleteAccident")
    public String delete(@RequestParam("id") int id) {
        accidentService.deleteById(id);
        return "redirect:/";
    }
}