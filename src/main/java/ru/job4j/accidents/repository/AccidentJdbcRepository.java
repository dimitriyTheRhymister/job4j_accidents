package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcRepository {
    private final JdbcTemplate jdbc;

    public Accident save(Accident accident) {
        // Вариант 1: Используем RETURNING id (лучший для PostgreSQL)
        String sql = "INSERT INTO accidents (name, text, address, type_id) VALUES (?, ?, ?, ?) RETURNING id";

        Integer generatedId = jdbc.queryForObject(
                sql,
                Integer.class,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId()
        );

        if (generatedId != null) {
            accident.setId(generatedId);
        }

        // Сохраняем правила для инцидента
        saveRulesForAccident(accident);

        return accident;
    }

    private void saveRulesForAccident(Accident accident) {
        for (Rule rule : accident.getRules()) {
            jdbc.update("INSERT INTO accident_rules (accident_id, rule_id) VALUES (?, ?)",
                    accident.getId(), rule.getId());
        }
    }

    public List<Accident> findAll() {
        String sql = """
            SELECT a.id, a.name, a.text, a.address, 
                   at.id as type_id, at.name as type_name,
                   r.id as rule_id, r.name as rule_name
            FROM accidents a
            LEFT JOIN accident_types at ON a.type_id = at.id
            LEFT JOIN accident_rules ar ON a.id = ar.accident_id
            LEFT JOIN rules r ON ar.rule_id = r.id
            ORDER BY a.id
            """;

        Map<Integer, Accident> accidentMap = new HashMap<>();

        jdbc.query(sql, rs -> {
            int accidentId = rs.getInt("id");

            Accident accident = accidentMap.get(accidentId);
            if (accident == null) {
                accident = new Accident();
                accident.setId(accidentId);
                accident.setName(rs.getString("name"));
                accident.setText(rs.getString("text"));
                accident.setAddress(rs.getString("address"));

                // Устанавливаем тип
                AccidentType type = new AccidentType();
                type.setId(rs.getInt("type_id"));
                type.setName(rs.getString("type_name"));
                accident.setType(type);

                accident.setRules(new HashSet<>());
                accidentMap.put(accidentId, accident);
            }

            // Добавляем правило, если оно есть
            int ruleId = rs.getInt("rule_id");
            if (!rs.wasNull()) {
                Rule rule = new Rule();
                rule.setId(ruleId);
                rule.setName(rs.getString("rule_name"));
                accident.getRules().add(rule);
            }
        });

        return new ArrayList<>(accidentMap.values());
    }

    public Optional<Accident> findById(int id) {
        String sql = """
            SELECT a.id, a.name, a.text, a.address, 
                   at.id as type_id, at.name as type_name,
                   r.id as rule_id, r.name as rule_name
            FROM accidents a
            LEFT JOIN accident_types at ON a.type_id = at.id
            LEFT JOIN accident_rules ar ON a.id = ar.accident_id
            LEFT JOIN rules r ON ar.rule_id = r.id
            WHERE a.id = ?
            """;

        List<Accident> accidents = new ArrayList<>();
        Map<Integer, Accident> accidentMap = new HashMap<>();

        jdbc.query(sql, ps -> ps.setInt(1, id), rs -> {
            int accidentId = rs.getInt("id");

            Accident accident = accidentMap.get(accidentId);
            if (accident == null) {
                accident = new Accident();
                accident.setId(accidentId);
                accident.setName(rs.getString("name"));
                accident.setText(rs.getString("text"));
                accident.setAddress(rs.getString("address"));

                AccidentType type = new AccidentType();
                type.setId(rs.getInt("type_id"));
                type.setName(rs.getString("type_name"));
                accident.setType(type);

                accident.setRules(new HashSet<>());
                accidentMap.put(accidentId, accident);
                accidents.add(accident);
            }

            int ruleId = rs.getInt("rule_id");
            if (!rs.wasNull()) {
                Rule rule = new Rule();
                rule.setId(ruleId);
                rule.setName(rs.getString("rule_name"));
                accident.getRules().add(rule);
            }
        });

        return accidents.isEmpty() ? Optional.empty() : Optional.of(accidents.get(0));
    }

    public boolean update(Accident accident) {
        // Обновляем основную информацию
        int rowsUpdated = jdbc.update(
                "UPDATE accidents SET name = ?, text = ?, address = ?, type_id = ? WHERE id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()
        );

        if (rowsUpdated > 0) {
            // Удаляем старые связи
            jdbc.update("DELETE FROM accident_rules WHERE accident_id = ?", accident.getId());

            // Сохраняем новые связи
            saveAccidentRules(accident.getId(), accident.getRules());
            return true;
        }
        return false;
    }

    public boolean deleteById(int id) {
        // Каскадное удаление связей через ON DELETE CASCADE
        int rowsDeleted = jdbc.update("DELETE FROM accidents WHERE id = ?", id);
        return rowsDeleted > 0;
    }

    private void saveAccidentRules(int accidentId, Set<Rule> rules) {
        if (rules != null && !rules.isEmpty()) {
            String sql = "INSERT INTO accident_rules (accident_id, rule_id) VALUES (?, ?)";
            List<Object[]> batchArgs = new ArrayList<>();

            for (Rule rule : rules) {
                batchArgs.add(new Object[]{accidentId, rule.getId()});
            }

            jdbc.batchUpdate(sql, batchArgs);
        }
    }
}