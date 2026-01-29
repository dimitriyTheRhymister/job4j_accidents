package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class RuleJdbcRepository {
    private final JdbcTemplate jdbc;

    public List<Rule> findAll() {
        return jdbc.query(
                "SELECT id, name FROM rules ORDER BY id",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                }
        );
    }

    public Optional<Rule> findById(int id) {
        List<Rule> rules = jdbc.query(
                "SELECT id, name FROM rules WHERE id = ?",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                id
        );
        return rules.isEmpty() ? Optional.empty() : Optional.of(rules.get(0));
    }

    public Set<Rule> findByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptySet();
        }

        String inClause = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = String.format("SELECT id, name FROM rules WHERE id IN (%s)", inClause);

        List<Rule> rules = jdbc.query(
                sql,
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                },
                ids.toArray()
        );

        return new HashSet<>(rules);
    }
}