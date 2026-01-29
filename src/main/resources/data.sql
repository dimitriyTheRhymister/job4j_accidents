-- Заполняем типы инцидентов (без ID)
INSERT INTO accident_types (name) VALUES
    ('Две машины'),
    ('Машина и человек'),
    ('Машина и велосипед'),
    ('Одно транспортное средство')
ON CONFLICT (name) DO NOTHING;

-- Заполняем статьи нарушений (без ID)
INSERT INTO rules (name) VALUES
    ('Статья. 1'),
    ('Статья. 2'),
    ('Статья. 3'),
    ('Статья. 12.9 - Превышение скорости'),
    ('Статья. 12.12 - Проезд на красный'),
    ('Статья. 12.19 - Нарушение правил парковки')
ON CONFLICT (name) DO NOTHING;

-- Заполняем тестовые инциденты (без ID)
INSERT INTO accidents (name, text, address, type_id) VALUES
    ('Нарушение ПДД', 'Превышение скорости на 20 км/ч', 'ул. Ленина, д. 10',
        (SELECT id FROM accident_types WHERE name = 'Две машины')),
    ('ДТП', 'Столкновение двух автомобилей', 'пр. Мира, д. 25',
        (SELECT id FROM accident_types WHERE name = 'Машина и человек')),
    ('Наезд на велосипедиста', 'Водитель не уступил дорогу велосипедисту', 'ул. Спортивная, д. 15',
        (SELECT id FROM accident_types WHERE name = 'Машина и велосипед'))
ON CONFLICT DO NOTHING;

-- Связываем инциденты со статьями
INSERT INTO accident_rules (accident_id, rule_id) VALUES
    ((SELECT id FROM accidents WHERE name = 'Нарушение ПДД'),
     (SELECT id FROM rules WHERE name = 'Статья. 12.9 - Превышение скорости')),
    ((SELECT id FROM accidents WHERE name = 'ДТП'),
     (SELECT id FROM rules WHERE name = 'Статья. 1')),
    ((SELECT id FROM accidents WHERE name = 'ДТП'),
     (SELECT id FROM rules WHERE name = 'Статья. 2')),
    ((SELECT id FROM accidents WHERE name = 'Наезд на велосипедиста'),
     (SELECT id FROM rules WHERE name = 'Статья. 3')),
    ((SELECT id FROM accidents WHERE name = 'Наезд на велосипедиста'),
     (SELECT id FROM rules WHERE name = 'Статья. 12.12 - Проезд на красный'))
ON CONFLICT DO NOTHING;

-- Обновляем sequences после вставки
SELECT setval('accidents_id_seq', COALESCE((SELECT MAX(id) FROM accidents), 0) + 1, false);
SELECT setval('accident_types_id_seq', COALESCE((SELECT MAX(id) FROM accident_types), 0) + 1, false);
SELECT setval('rules_id_seq', COALESCE((SELECT MAX(id) FROM rules), 0) + 1, false);