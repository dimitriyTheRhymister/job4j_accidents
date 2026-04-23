-- Добавляем роли
INSERT INTO authorities (authority) VALUES ('ROLE_USER') ON CONFLICT DO NOTHING;
INSERT INTO authorities (authority) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;

-- Добавляем типы инцидентов
INSERT INTO accident_types (name) VALUES ('ДТП') ON CONFLICT DO NOTHING;
INSERT INTO accident_types (name) VALUES ('Нарушение ПДД') ON CONFLICT DO NOTHING;
INSERT INTO accident_types (name) VALUES ('Пешеход') ON CONFLICT DO NOTHING;

-- Добавляем статьи правил
INSERT INTO rules (name) VALUES ('Статья 12.8 КоАП РФ') ON CONFLICT DO NOTHING;
INSERT INTO rules (name) VALUES ('Статья 12.9 КоАП РФ') ON CONFLICT DO NOTHING;
INSERT INTO rules (name) VALUES ('Статья 12.15 КоАП РФ') ON CONFLICT DO NOTHING;