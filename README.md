# Job4j Accidents Project

Проект для курса Job4j по разработке приложения "Автонарушители".

## Технологии:
- Java 21
- Spring Boot 3.2.0
- Maven
- PostgreSQL
- Thymeleaf
- Spring Data JPA

## Запуск проекта:

### 1. Требования:
- Java 21
- Maven 3.8+
- PostgreSQL 14+

### 2. Настройка базы данных:
```sql
CREATE DATABASE accidents;
CREATE USER accidents_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE accidents TO accidents_user;