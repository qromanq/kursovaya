package com.inventory.config;

import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Компонент для загрузки тестовых данных при запуске приложения
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Очищаем существующие данные
        productRepository.deleteAll();
        
        // Загружаем тестовые данные
        loadTestData();
        
        System.out.println("✅ Загружено " + productRepository.count() + " тестовых товаров");
    }

    private void loadTestData() {
        // Создаем тестовые товары
        Product[] products = {
            createProduct("Ноутбук Dell Inspiron 15", "Компьютеры", 5, 45000, "Dell Technologies", 3, "Ноутбук для офисной работы с процессором Intel Core i5", "1234567890123", "Склад А-1", Product.ProductStatus.IN_STOCK),
            createProduct("Мышь Logitech MX Master 3", "Периферия", 20, 1500, "Logitech", 10, "Беспроводная мышь для профессионалов", "2345678901234", "Склад А-2", Product.ProductStatus.IN_STOCK),
            createProduct("Клавиатура механическая Keychron K2", "Периферия", 15, 2500, "Keychron", 8, "Механическая клавиатура с подсветкой", "3456789012345", "Склад А-2", Product.ProductStatus.IN_STOCK),
            createProduct("Монитор Samsung 24\" Full HD", "Мониторы", 8, 15000, "Samsung Electronics", 5, "IPS монитор 24 дюйма с разрешением 1920x1080", "4567890123456", "Склад Б-1", Product.ProductStatus.IN_STOCK),
            createProduct("Принтер HP LaserJet Pro", "Оргтехника", 3, 12000, "HP Inc.", 2, "Лазерный принтер для офиса", "5678901234567", "Склад Б-2", Product.ProductStatus.IN_STOCK),
            createProduct("Веб-камера Logitech C920", "Периферия", 12, 3500, "Logitech", 6, "HD веб-камера для видеоконференций", "6789012345678", "Склад А-2", Product.ProductStatus.IN_STOCK),
            createProduct("Наушники Sony WH-1000XM4", "Аудио", 25, 4500, "Sony Corporation", 15, "Беспроводные наушники с шумоподавлением", "7890123456789", "Склад В-1", Product.ProductStatus.IN_STOCK),
            createProduct("Планшет iPad Air", "Планшеты", 7, 35000, "Apple Inc.", 4, "Планшет Apple iPad Air с дисплеем 10.9 дюймов", "8901234567890", "Склад В-2", Product.ProductStatus.IN_STOCK),
            createProduct("Смартфон Samsung Galaxy S23", "Смартфоны", 10, 25000, "Samsung Electronics", 6, "Флагманский смартфон с камерой 50 МП", "9012345678901", "Склад В-2", Product.ProductStatus.IN_STOCK),
            createProduct("Роутер TP-Link Archer AX73", "Сетевое оборудование", 6, 2800, "TP-Link", 4, "Wi-Fi 6 роутер для дома и офиса", "0123456789012", "Склад Б-3", Product.ProductStatus.IN_STOCK),
            createProduct("SSD Samsung 970 EVO Plus 1TB", "Комплектующие", 2, 8500, "Samsung Electronics", 5, "Твердотельный накопитель M.2 NVMe", "1357924680135", "Склад А-3", Product.ProductStatus.IN_STOCK),
            createProduct("Оперативная память Corsair 16GB", "Комплектующие", 1, 4200, "Corsair", 8, "DDR4-3200 16GB (2x8GB) для игровых ПК", "2468135792468", "Склад А-3", Product.ProductStatus.IN_STOCK),
            createProduct("Видеокарта NVIDIA RTX 4060", "Комплектующие", 0, 32000, "NVIDIA Corporation", 3, "Игровая видеокарта среднего класса", "3691472583691", "Склад А-3", Product.ProductStatus.OUT_OF_STOCK),
            createProduct("Кабель USB-C 2м", "Аксессуары", 50, 350, "Belkin", 25, "Кабель для зарядки и передачи данных", "4815926374815", "Склад Г-1", Product.ProductStatus.IN_STOCK),
            createProduct("Подставка для ноутбука", "Аксессуары", 18, 1200, "Rain Design", 10, "Алюминиевая подставка с охлаждением", "5927384615927", "Склад Г-1", Product.ProductStatus.IN_STOCK)
        };

        // Сохраняем все товары
        for (Product product : products) {
            productRepository.save(product);
        }
    }

    private Product createProduct(String name, String category, Integer quantity, Integer price, 
                                String supplier, Integer minStock, String description, 
                                String barcode, String location, Product.ProductStatus status) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setSupplier(supplier);
        product.setMinStock(minStock);
        product.setDescription(description);
        product.setBarcode(barcode);
        product.setLocation(location);
        product.setStatus(status);
        return product;
    }
}