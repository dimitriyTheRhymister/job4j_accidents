-- Таблица типов инцидентов
CREATE TABLE IF NOT EXISTS accident_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Таблица статей нарушений
CREATE TABLE IF NOT EXISTS rules (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Таблица инцидентов
CREATE TABLE IF NOT EXISTS accidents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    address VARCHAR(500) NOT NULL,
    type_id INT REFERENCES accident_types(id)
);

-- Связующая таблица для many-to-many (инцидент <-> статья)
CREATE TABLE IF NOT EXISTS accident_rules (
    accident_id INT REFERENCES accidents(id) ON DELETE CASCADE,
    rule_id INT REFERENCES rules(id) ON DELETE CASCADE,
    PRIMARY KEY (accident_id, rule_id)
);