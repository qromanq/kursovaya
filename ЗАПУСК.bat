@echo off
chcp 65001 >nul
title Система управления запасами (Java + Spring Boot)

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║                 СИСТЕМА УПРАВЛЕНИЯ ЗАПАСАМИ                  ║
echo ║                Java + Spring Boot + Gradle                   ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

REM Переходим в директорию скрипта
cd /d "%~dp0"

REM Проверяем наличие Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java не найдена. Начинаем установку...
    goto :install_java
) else (
    echo ✅ Java найдена
    java -version
    echo.
    goto :check_gradle
)

:install_java
echo 🔄 Попытка установки Java через winget...
winget install Microsoft.OpenJDK.17 --silent --accept-package-agreements --accept-source-agreements >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Java установлена через winget
    goto :wait_and_check_java
)

echo 🔄 Попытка установки Java через Chocolatey...
choco install openjdk17 -y >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Java установлена через Chocolatey
    goto :wait_and_check_java
)

echo 🔄 Скачиваем Java с официального сайта...
powershell -Command "try { [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.msi' -OutFile 'java-installer.msi' -UseBasicParsing } catch { exit 1 }" >nul 2>&1

if not exist "java-installer.msi" (
    echo ❌ Не удалось скачать Java
    echo 📋 Пожалуйста, установите Java 17+ вручную:
    echo    https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

echo 🔄 Устанавливаем Java...
msiexec /i "java-installer.msi" /quiet /norestart
del "java-installer.msi" >nul 2>&1

:wait_and_check_java
echo ⏳ Ожидаем завершения установки Java...
timeout /t 45 /nobreak >nul

java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Установка Java не удалась
    echo 📋 Установите Java вручную с https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

:check_gradle
echo ✅ Java готова к работе
echo.

REM Проверяем наличие Gradle Wrapper
if exist "gradlew.bat" (
    echo ✅ Gradle Wrapper найден
    goto :build_project
)

echo 🔄 Создаем Gradle Wrapper...
if exist gradle\wrapper\gradle-wrapper.properties (
    echo ✅ Gradle Wrapper уже настроен
) else (
    echo 🔄 Инициализируем Gradle проект...
    gradle wrapper --gradle-version 8.5 >nul 2>&1
    if %errorlevel% neq 0 (
        echo ❌ Ошибка создания Gradle Wrapper
        echo 📋 Установите Gradle вручную: https://gradle.org/install/
        pause
        exit /b 1
    )
)

:build_project
echo 🔄 Собираем проект...
gradlew.bat build -x test --quiet
if %errorlevel% neq 0 (
    echo ❌ Ошибка сборки проекта
    echo 🔄 Попробуем собрать с подробным выводом...
    gradlew.bat build -x test
    pause
    exit /b 1
)

echo ✅ Проект успешно собран
echo.

echo 🔄 Создаем директории данных...
if not exist "data" mkdir data
if not exist "logs" mkdir logs

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║                      ЗАПУСК СЕРВЕРА                         ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.
echo 🌐 Сервер запускается на: http://localhost:8080
echo 🔄 Браузер откроется автоматически через 5 секунд...
echo 📊 Мониторинг доступен: http://localhost:8080/metrics
echo 🗄️ H2 Console: http://localhost:8080/h2-console
echo ⚠️  Для остановки нажмите Ctrl+C
echo.

REM Открываем браузер через 5 секунд
start "" cmd /c "timeout /t 5 /nobreak >nul & start http://localhost:8080"

REM Запускаем приложение
gradlew.bat bootRun --quiet

echo.
echo 🛑 Сервер остановлен
pause