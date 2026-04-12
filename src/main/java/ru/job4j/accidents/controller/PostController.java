package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.job4j.accidents.model.Post;
import ru.job4j.accidents.service.PostService;

import java.util.Optional;

@Controller
public class PostController {
    private final PostService posts;

    public PostController(PostService posts) {
        this.posts = posts;
    }

    @RequestMapping(value="/post/{id}", method = RequestMethod.GET)
    public String get(@PathVariable int id, Model model) {
        Optional<Post> postOptional = posts.findById(id);
        if (postOptional.isEmpty()) {
            return "404"; // или выбросить исключение
        }
        model.addAttribute("post", postOptional.get());
        return "post";
    }

    @RequestMapping(value="/post/create", method = RequestMethod.POST)
    public String create(@ModelAttribute Post post) {
        Post savedPost = posts.create(post);  // ← СОХРАНЯЕМ возвращенный объект!
        return "redirect:/post/" + savedPost.getId();  // ← Используем savedPost.getId()
    }
}