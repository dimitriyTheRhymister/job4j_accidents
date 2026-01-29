package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcRepository {
    private final JdbcTemplate jdbc;

    public List<AccidentType> findAll() {
        return jdbc.query(
                "SELECT id, name FROM accident_types ORDER BY id",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                }
        );
    }

    public Optional<AccidentType> findById(int id) {
        List<AccidentType> types = jdbc.query(
                "SELECT id, name FROM accident_types WHERE id = ?",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                },
                id
        );
        return types.isEmpty() ? Optional.empty() : Optional.of(types.get(0));
    }
}