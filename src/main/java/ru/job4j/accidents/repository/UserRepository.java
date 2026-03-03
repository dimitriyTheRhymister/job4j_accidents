package ru.job4j.accidents.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.accidents.model.User;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    User findByUsername(String username);
}