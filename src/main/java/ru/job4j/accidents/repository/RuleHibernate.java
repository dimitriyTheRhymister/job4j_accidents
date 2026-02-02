package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private final SessionFactory sf;

    public List<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule order by id", Rule.class).list();
        }
    }

    public Set<Rule> findByIds(Set<Integer> ids) {
        try (Session session = sf.openSession()) {
            return new java.util.HashSet<>(session
                    .createQuery("from Rule where id in :ids", Rule.class)
                    .setParameter("ids", ids)
                    .list());
        }
    }

    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            return Optional.ofNullable(session.get(Rule.class, id));
        }
    }
}