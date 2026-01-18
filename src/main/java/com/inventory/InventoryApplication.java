package com.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения системы управления запасами
 */
@SpringBootApplication
public class InventoryApplication {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Система управления запасами");
        System.out.println("   Запуск сервера...");
        System.out.println("========================================");
        
        SpringApplication.run(InventoryApplication.class, args);
        
        System.out.println("Сервер запущен на: http://localhost:8080");
        System.out.println("Для остановки нажмите Ctrl+C");
    }
}