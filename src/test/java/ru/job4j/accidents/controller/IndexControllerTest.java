package ru.job4j.accidents.controller;

//package ru.job4j.forum.control;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.AccidentApplication;

@SpringBootTest(classes = AccidentApplication.class)
@AutoConfigureMockMvc
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/index"))             // 1. Выполняет GET запрос к /index
                .andDo(print())                                   // 2. Выводит детали запроса/ответа в консоль
                .andExpect(status().isOk())                       // 3. Ожидает HTTP статус 200 OK
                .andExpect(view().name("index"))  // 4. Ожидает имя представления index
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("accidents"));
    }

    @Test
    @WithMockUser
    public void shouldReturnRootPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void shouldReturnUserInModel() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", "testUser"));
    }
}