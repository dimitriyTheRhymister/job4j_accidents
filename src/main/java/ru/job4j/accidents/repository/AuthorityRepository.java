package ru.job4j.accidents.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.accidents.model.Authority;

public interface AuthorityRepository extends ListCrudRepository<Authority, Integer> {
    Authority findByAuthority(String authority);
}