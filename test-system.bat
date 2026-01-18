@echo off
echo ========================================
echo   ТЕСТ СИСТЕМЫ УПРАВЛЕНИЯ ЗАПАСАМИ
echo   Java + Spring Boot + Gradle
echo ========================================
echo.

REM Проверяем наличие файлов
echo Проверяем файлы проекта...

if not exist "build.gradle" (
    echo ❌ Файл build.gradle не найден
    goto :error
)

if not exist "src\main\java\com\inventory\InventoryApplication.java" (
    echo ❌ Главный класс Java не найден
    goto :error
)

if not exist "src\main\resources\application.properties" (
    echo ❌ Конфигурация Spring Boot не найдена
    goto :error
)

if not exist "gradlew.bat" (
    echo ❌ Gradle Wrapper не найден
    goto :error
)

echo ✅ Все файлы проекта на месте

REM Проверяем Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java не найдена
    echo 📋 Запустите ЗАПУСК.bat для автоматической установки
    goto :error
)

echo ✅ Java найдена
java -version

echo.
echo ========================================
echo   ВСЕ ПРОВЕРКИ ПРОЙДЕНЫ УСПЕШНО!
echo ========================================
echo.
echo Система готова к запуску.
echo Запустите ЗАПУСК.bat для старта сервера.
echo.
pause
exit /b 0

:error
echo.
echo ========================================
echo   ОБНАРУЖЕНЫ ПРОБЛЕМЫ
echo ========================================
echo.
echo Проверьте целостность файлов проекта.
echo.
pause
exit /b 1