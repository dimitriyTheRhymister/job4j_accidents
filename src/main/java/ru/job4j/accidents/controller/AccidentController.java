package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = accidentService.findAllTypes();
        model.addAttribute("types", types);
        model.addAttribute("accident", new Accident());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident,
                       @RequestParam("type.id") int typeId) {
        accidentService.findTypeById(typeId).ifPresent(accident::setType);
        accidentService.save(accident);
        return "redirect:/";
    }

    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("id") int id, Model model) {
        Optional<Accident> accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            return "redirect:/";
        }

        List<AccidentType> types = accidentService.findAllTypes();
        model.addAttribute("types", types);
        model.addAttribute("accident", accidentOptional.get());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident,
                         @RequestParam("type.id") int typeId) {
        accidentService.findTypeById(typeId).ifPresent(accident::setType);
        accidentService.update(accident);
        return "redirect:/";
    }

    @GetMapping("/deleteAccident")
    public String delete(@RequestParam("id") int id) {
        accidentService.deleteById(id);
        return "redirect:/";
    }
}