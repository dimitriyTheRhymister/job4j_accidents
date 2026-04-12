package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.AccidentApplication;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

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
public class RegControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private UserRepository users;

    @MockBean
    private AuthorityRepository authorities;

    // ========== GET ТЕСТ ==========

    @Test
    public void shouldReturnRegistrationPage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    // ========== НОВЫЙ POST ТЕСТ ==========

    @Test
    public void shouldRegisterNewUser() throws Exception {
        // Подготовка
        String rawPassword = "secret123";
        String encodedPassword = "encodedPassword123";
        Authority userRole = new Authority();
        userRole.setId(1);
        userRole.setAuthority("ROLE_USER");

        when(encoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(authorities.findByAuthority("ROLE_USER")).thenReturn(userRole);
        when(users.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1);
            return user;
        });

        // Действие
        this.mockMvc.perform(post("/reg")
                        .param("username", "newuser")
                        .param("password", rawPassword))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        // Проверка
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(users).save(argument.capture());

        User savedUser = argument.getValue();
        assertThat(savedUser.getUsername()).isEqualTo("newuser");
        assertThat(savedUser.getPassword()).isEqualTo(encodedPassword);
        assertThat(savedUser.isEnabled()).isTrue();
        assertThat(savedUser.getAuthority()).isEqualTo(userRole);

        verify(encoder).encode(rawPassword);
        verify(authorities).findByAuthority("ROLE_USER");
    }
}