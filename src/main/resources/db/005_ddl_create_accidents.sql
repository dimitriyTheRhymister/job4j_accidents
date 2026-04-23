CREATE TABLE IF NOT EXISTS accidents (
    id SERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    text TEXT,
    address VARCHAR(200),
    type_id INTEGER REFERENCES accident_types(id)
);