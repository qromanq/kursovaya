# Система управления запасами для малого предприятия

## Описание проекта

Минимальный прототип веб-системы управления запасами, разработанный в рамках курсовой работы по дисциплине "Инструментальные средства информационных систем".

## Функциональность

- Просмотр списка товаров
- Добавление новых товаров
- Обновление количества товаров
- Удаление товаров
- Простая веб-интерфейс

## Технологии

- **Backend**: Node.js + Express
- **Frontend**: HTML/CSS/JavaScript
- **База данных**: SQLite (встроенная)
- **Контейнеризация**: Docker
- **CI/CD**: GitHub Actions
- **Тестирование**: Jest
- **Сборка**: npm scripts

## Архитектура

```
inventory-system/
├── src/
│   ├── app.js          # Основное приложение
│   ├── database.js     # Работа с БД
│   └── public/         # Статические файлы
├── tests/              # Модульные тесты
├── Dockerfile          # Docker образ
├── docker-compose.yml  # Локальный запуск
├── package.json        # Зависимости
└── .github/workflows/  # CI/CD
```

## Быстрый запуск

### Вариант 1: Автоматический запуск (рекомендуется)

**Windows (cmd/PowerShell):**
```cmd
# Просто дважды кликните на файл start.bat
# ИЛИ запустите в командной строке:
start.bat
```

**PowerShell:**
```powershell
# Разрешите выполнение скриптов (если нужно):
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Запустите скрипт:
.\start.ps1
```

**Если Node.js уже установлен:**
```cmd
npm run start:auto
```

### Вариант 2: Docker

```bash
# Запуск через Docker Compose
docker-compose up --build
```

### Вариант 3: Ручная установка

```bash
# Установка зависимостей
npm install

# Запуск приложения
npm start
```

## Использование

После запуска откройте браузер и перейдите по адресу: http://localhost:3000

## Тестирование

```bash
# Запуск тестов
npm test

# Запуск тестов с покрытием
npm run test:coverage
```

## Мониторинг

Система включает базовый мониторинг:
- Логирование запросов
- Отслеживание ошибок
- Метрики производительности (доступны на /metrics)

## Структура базы данных

```sql
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    price REAL NOT NULL DEFAULT 0.0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

## API Endpoints

- `GET /api/products` - Получить все товары
- `POST /api/products` - Добавить товар
- `PUT /api/products/:id` - Обновить товар
- `DELETE /api/products/:id` - Удалить товар

## Разработка

Проект настроен для разработки с автоматической перезагрузкой:

```bash
npm run dev
```

## CI/CD

Настроен GitHub Actions pipeline для:
- Автоматической сборки
- Запуска тестов
- Создания Docker образа
- Деплоя (при необходимости)

## Автор

Курсовая работа по дисциплине "Инструментальные средства информационных систем"

## Лицензия

MIT