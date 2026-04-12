package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.AccidentApplication;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AccidentApplication.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @MockBean
    private AccidentTypeService accidentTypeService;

    @MockBean
    private RuleService ruleService;

    // ========== GET ТЕСТЫ (уже есть) ==========

    @Test
    @WithMockUser
    public void shouldReturnCreateAccidentPage() throws Exception {
        when(accidentTypeService.findAll()).thenReturn(List.of());
        when(ruleService.findAll()).thenReturn(List.of());

        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attributeExists("accident"));

        verify(accidentTypeService).findAll();
        verify(ruleService).findAll();
    }

    @Test
    @WithMockUser
    public void shouldReturnEditAccidentPage() throws Exception {
        Accident accident = new Accident();
        accident.setId(1);
        accident.setName("Test Accident");

        when(accidentService.findById(1)).thenReturn(Optional.of(accident));
        when(accidentTypeService.findAll()).thenReturn(List.of());
        when(ruleService.findAll()).thenReturn(List.of());

        this.mockMvc.perform(get("/editAccident").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"))
                .andExpect(model().attributeExists("accident"))
                .andExpect(model().attribute("accident", accident));

        verify(accidentService).findById(1);
        verify(accidentTypeService).findAll();
        verify(ruleService).findAll();
    }

    @Test
    @WithMockUser
    public void shouldRedirectWhenAccidentNotFound() throws Exception {
        when(accidentService.findById(999)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/editAccident").param("id", "999"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(accidentService).findById(999);
    }

    @Test
    @WithMockUser
    public void shouldDeleteAccidentAndRedirect() throws Exception {
        this.mockMvc.perform(get("/deleteAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(accidentService).deleteById(1);
    }

    // ========== НОВЫЕ POST ТЕСТЫ ==========

    @Test
    @WithMockUser
    public void shouldCreateAccident() throws Exception {
        // Подготовка
        AccidentType type = new AccidentType();
        type.setId(1);
        type.setName("ДТП");

        Rule rule = new Rule();
        rule.setId(1);
        rule.setName("Нарушение ПДД");

        Accident savedAccident = new Accident();
        savedAccident.setId(1);
        savedAccident.setName("Авария на перекрестке");
        savedAccident.setText("Столкновение двух авто");
        savedAccident.setAddress("ул. Ленина, 1");
        savedAccident.setType(type);
        savedAccident.setRules(Set.of(rule));

        when(accidentTypeService.findById(1)).thenReturn(Optional.of(type));
        when(ruleService.findByIds(Set.of(1))).thenReturn(Set.of(rule));
        when(accidentService.save(any(Accident.class))).thenReturn(savedAccident);

        // Действие
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "Авария на перекрестке")
                        .param("text", "Столкновение двух авто")
                        .param("address", "ул. Ленина, 1")
                        .param("type.id", "1")
                        .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Проверка
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(argument.capture());

        assertThat(argument.getValue().getName()).isEqualTo("Авария на перекрестке");
        assertThat(argument.getValue().getText()).isEqualTo("Столкновение двух авто");
        assertThat(argument.getValue().getAddress()).isEqualTo("ул. Ленина, 1");
        assertThat(argument.getValue().getType()).isEqualTo(type);
        assertThat(argument.getValue().getRules()).contains(rule);
    }

    @Test
    @WithMockUser
    public void shouldUpdateAccident() throws Exception {
        // Подготовка
        AccidentType type = new AccidentType();
        type.setId(2);
        type.setName("Поломка");

        Accident existingAccident = new Accident();
        existingAccident.setId(1);
        existingAccident.setName("Обновленная авария");
        existingAccident.setText("Новое описание");
        existingAccident.setAddress("Новый адрес");
        existingAccident.setType(type);

        when(accidentTypeService.findById(2)).thenReturn(Optional.of(type));
        when(accidentService.save(any(Accident.class))).thenReturn(existingAccident);

        // Действие
        this.mockMvc.perform(post("/updateAccident")
                        .param("id", "1")
                        .param("name", "Обновленная авария")
                        .param("text", "Новое описание")
                        .param("address", "Новый адрес")
                        .param("type.id", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Проверка
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(argument.capture());

        assertThat(argument.getValue().getId()).isEqualTo(1);
        assertThat(argument.getValue().getName()).isEqualTo("Обновленная авария");
        assertThat(argument.getValue().getType().getId()).isEqualTo(2);
    }

    @Test
    @WithMockUser
    public void shouldCreateAccidentWithoutRules() throws Exception {
        // Подготовка
        AccidentType type = new AccidentType();
        type.setId(1);
        type.setName("ДТП");

        when(accidentTypeService.findById(1)).thenReturn(Optional.of(type));
        when(accidentService.save(any(Accident.class))).thenReturn(new Accident());

        // Действие
        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "Авария без правил")
                        .param("text", "Описание")
                        .param("address", "Адрес")
                        .param("type.id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Проверка
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(argument.capture());

        assertThat(argument.getValue().getRules()).isEmpty();
    }
}