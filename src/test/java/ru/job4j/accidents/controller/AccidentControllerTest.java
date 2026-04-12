package ru.job4j.accidents.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.AccidentApplication;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;

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

        verify(accidentTypeService, times(1)).findAll();
        verify(ruleService, times(1)).findAll();
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

        verify(accidentService, times(1)).findById(1);
        verify(accidentTypeService, times(1)).findAll();
        verify(ruleService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void shouldRedirectWhenAccidentNotFound() throws Exception {
        when(accidentService.findById(999)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/editAccident").param("id", "999"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(accidentService, times(1)).findById(999);
    }

    @Test
    @WithMockUser
    public void shouldDeleteAccidentAndRedirect() throws Exception {
        this.mockMvc.perform(get("/deleteAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(accidentService, times(1)).deleteById(1);
    }
}