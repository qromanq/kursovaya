# Система управления запасами

Веб-система управления запасами на Java Spring Boot.

## Технологии

- Java 17 + Spring Boot 3.2
- H2 Database
- Gradle
- Docker

## Запуск

```bash
./gradlew bootRun
```

Или через Docker:
```bash
docker build -t inventory-system .
docker run -p 8080:8080 inventory-system
```

Приложение доступно по адресу: http://localhost:8080

## API

- `GET /api/products` - список товаров
- `POST /api/products` - добавить товар
- `PUT /api/products/{id}` - обновить товар
- `DELETE /api/products/{id}` - удалить товар