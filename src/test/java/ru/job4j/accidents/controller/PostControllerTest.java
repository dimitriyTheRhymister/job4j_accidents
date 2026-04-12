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
import ru.job4j.accidents.model.Post;
import ru.job4j.accidents.service.PostService;

import java.time.LocalDateTime;
import java.util.Optional;

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
public class PostControllerTest {

    @MockBean
    private PostService posts;

    @Autowired
    private MockMvc mockMvc;

    // ========== ТЕСТ 1: СОЗДАНИЕ ПОСТА (POST) ==========

    @Test
    @WithMockUser
    public void shouldCreatePost() throws Exception {
        // Подготовка
        Post savedPost = new Post();
        savedPost.setId(1);
        savedPost.setTitle("Куплю ладу-гранту. Дорого.");
        savedPost.setContent("Срочно!");
        savedPost.setCreated(LocalDateTime.now());

        when(posts.create(any(Post.class))).thenReturn(savedPost);

        // Выполнение
        this.mockMvc.perform(post("/post/create")
                        .param("title", "Куплю ладу-гранту. Дорого.")
                        .param("content", "Срочно!"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/post/1"));

        // Проверка
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(posts).create(argument.capture());
        assertThat(argument.getValue().getTitle()).isEqualTo("Куплю ладу-гранту. Дорого.");
        assertThat(argument.getValue().getContent()).isEqualTo("Срочно!");
    }

    // ========== ТЕСТ 2: ПРОСМОТР ПОСТА (GET) ==========

    @Test
    @WithMockUser
    public void shouldGetPostById() throws Exception {
        // Подготовка
        Post post = new Post();
        post.setId(1);
        post.setTitle("Тестовый пост");
        post.setContent("Содержимое тестового поста");
        post.setCreated(LocalDateTime.now());

        when(posts.findById(1)).thenReturn(Optional.of(post));

        // Выполнение
        this.mockMvc.perform(get("/post/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post", post));

        verify(posts).findById(1);
    }

    // ========== ТЕСТ 3: ПОСТ НЕ НАЙДЕН (GET) ==========

    @Test
    @WithMockUser
    public void shouldReturnEmptyWhenPostNotFound() throws Exception {
        when(posts.findById(999)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/post/999"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("404"));  // ← ИЗМЕНИТЬ С "post" НА "404"
    }
}