package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {
    private final SessionFactory sf;

    public List<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType order by id", AccidentType.class).list();
        }
    }

    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            return Optional.ofNullable(session.get(AccidentType.class, id));
        }
    }
}