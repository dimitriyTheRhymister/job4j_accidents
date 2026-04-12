package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Post;
import ru.job4j.accidents.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Post update(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void deleteById(int id) {
        postRepository.deleteById(id);
    }

    public List<Post> findByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }

    public List<Post> findLatestPosts() {
        return postRepository.findByOrderByCreatedDesc();
    }
}