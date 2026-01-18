# Используем официальный OpenJDK образ
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем Gradle Wrapper и build файлы
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Копируем исходный код
COPY src src

# Делаем gradlew исполняемым
RUN chmod +x ./gradlew

# Собираем приложение
RUN ./gradlew build -x test

# Открываем порт
EXPOSE 8080

# Устанавливаем переменные окружения
ENV SPRING_PROFILES_ACTIVE=production

# Запускаем приложение
CMD ["./gradlew", "bootRun"]