# Система управления запасами для малого предприятия

## Описание проекта

Минимальный прототип веб-системы управления запасами, разработанный в рамках курсовой работы по дисциплине "Инструментальные средства информационных систем".

## Функциональность

- Просмотр списка товаров
- Добавление новых товаров
- Обновление количества товаров
- Удаление товаров
- Веб-интерфейс с REST API
- Аналитика и отчеты

## Технологии

- **Backend**: Java 17 + Spring Boot 3.2
- **Frontend**: Thymeleaf + HTML/CSS/JavaScript
- **База данных**: H2 Database (встроенная)
- **Сборка**: Gradle 8.5
- **Контейнеризация**: Docker
- **CI/CD**: GitHub Actions
- **Тестирование**: JUnit 5 + Spring Boot Test

## Архитектура

```
inventory-system/
├── src/
│   ├── main/
│   │   ├── java/com/inventory/
│   │   │   ├── InventoryApplication.java    # Главный класс
│   │   │   ├── model/Product.java           # Модель товара
│   │   │   ├── repository/ProductRepository.java # JPA репозиторий
│   │   │   ├── service/ProductService.java  # Бизнес-логика
│   │   │   └── controller/                  # REST и Web контроллеры
│   │   └── resources/
│   │       ├── application.properties       # Конфигурация
│   │       ├── data.sql                     # Тестовые данные
│   │       └── templates/                   # Thymeleaf шаблоны
│   └── test/                               # Модульные тесты
├── build.gradle                            # Конфигурация Gradle
├── Dockerfile                              # Docker образ
└── gradlew.bat                            # Gradle Wrapper
```

## Быстрый запуск

### Вариант 1: Автоматический запуск (рекомендуется)

**Самый простой способ:**
```cmd
# Просто дважды кликните на файл:
ЗАПУСК.bat
```

**Альтернативы:**
```cmd
# Или используйте:
start.bat

# Или в PowerShell:
.\start.ps1
```

**Что происходит автоматически:**
- ✅ Поиск или установка Java 17+
- ✅ Настройка Gradle Wrapper
- ✅ Сборка проекта
- ✅ Настройка базы данных H2
- ✅ Запуск Spring Boot приложения
- ✅ Автоматическое открытие браузера

### Вариант 2: Docker

```bash
# Запуск через Docker
docker build -t inventory-system .
docker run -p 8080:8080 inventory-system
```

### Вариант 3: Ручная установка

```bash
# Если Java и Gradle уже установлены
./gradlew bootRun

# Или через JAR файл
./gradlew build
java -jar build/libs/inventory-system.jar
```

## Использование

После запуска откройте браузер и перейдите по адресу: http://localhost:8080

## Тестирование

```bash
# Запуск тестов
./gradlew test

# Запуск тестов с отчетом
./gradlew test jacocoTestReport
```

## Мониторинг

Система включает базовый мониторинг:
- Логирование запросов
- Отслеживание ошибок
- Метрики производительности (доступны на /metrics)

## Структура базы данных

```sql
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    price DOUBLE NOT NULL DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## API Endpoints

- `GET /api/products` - Получить все товары
- `GET /api/products/{id}` - Получить товар по ID
- `POST /api/products` - Добавить товар
- `PUT /api/products/{id}` - Обновить товар
- `DELETE /api/products/{id}` - Удалить товар
- `GET /api/products/search?name={name}` - Поиск товаров
- `GET /api/products/low-stock?threshold={number}` - Товары с низким запасом

## Разработка

Проект настроен для разработки с автоматической перезагрузкой:

```bash
./gradlew bootRun --continuous
```

## CI/CD

Настроен GitHub Actions pipeline для:
- Автоматической сборки с Gradle
- Запуска тестов JUnit
- Создания Docker образа
- Проверки качества кода

## Автор

Курсовая работа по дисциплине "Инструментальные средства информационных систем"

## Лицензия

MIT