package ru.job4j.accidents.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.accidents.model.Post;

import java.util.List;

public interface PostRepository extends ListCrudRepository<Post, Integer> {

    List<Post> findByTitleContaining(String title);

    List<Post> findByOrderByCreatedDesc();
}