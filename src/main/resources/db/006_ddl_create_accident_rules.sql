CREATE TABLE IF NOT EXISTS accident_rules (
    accident_id INTEGER REFERENCES accidents(id) ON DELETE CASCADE,
    rule_id INTEGER REFERENCES rules(id) ON DELETE CASCADE,
    PRIMARY KEY (accident_id, rule_id)
);