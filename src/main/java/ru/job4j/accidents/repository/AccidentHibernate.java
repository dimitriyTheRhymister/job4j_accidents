package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final SessionFactory sf;

    // ТОЛЬКО методы для Accident
    public Accident save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            if (accident.getId() == null || accident.getId() == 0) {
                session.save(accident);
            } else {
                session.merge(accident);
            }
            session.getTransaction().commit();
            return accident;
        }
    }

    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("SELECT DISTINCT a FROM Accident a " +
                            "LEFT JOIN FETCH a.type " +
                            "LEFT JOIN FETCH a.rules " +
                            "ORDER BY a.id", Accident.class)
                    .list();
        }
    }

    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            Accident accident = session.createQuery(
                            "SELECT DISTINCT a FROM Accident a " +
                                    "LEFT JOIN FETCH a.type " +
                                    "LEFT JOIN FETCH a.rules " +
                                    "WHERE a.id = :id", Accident.class)
                    .setParameter("id", id)
                    .uniqueResult();
            return Optional.ofNullable(accident);
        }
    }

    public boolean update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.merge(accident);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Accident accident = session.get(Accident.class, id);
            if (accident != null) {
                session.delete(accident);
                session.getTransaction().commit();
                return true;
            }
            session.getTransaction().rollback();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}