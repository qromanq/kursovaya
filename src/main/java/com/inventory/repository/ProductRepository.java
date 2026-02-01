package com.inventory.repository;

import com.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Репозиторий для работы с товарами
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Поиск товаров по названию (игнорируя регистр)
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Поиск товаров с количеством меньше указанного
     */
    List<Product> findByQuantityLessThan(Integer quantity);
    
    /**
     * Поиск товаров в диапазоне цен
     */
    List<Product> findByPriceBetween(Integer minPrice, Integer maxPrice);
    
    /**
     * Поиск товаров по категории
     */
    List<Product> findByCategoryIgnoreCase(String category);
    
    /**
     * Поиск товаров по поставщику
     */
    List<Product> findBySupplierContainingIgnoreCase(String supplier);
    
    /**
     * Поиск товаров по статусу
     */
    List<Product> findByStatus(Product.ProductStatus status);
    
    /**
     * Поиск товаров с низким запасом
     */
    @Query("SELECT p FROM Product p WHERE p.quantity < p.minStock")
    List<Product> findLowStockProducts();
    
    /**
     * Поиск товаров без запаса
     */
    List<Product> findByQuantityLessThanEqual(Integer quantity);
    
    /**
     * Поиск товаров по штрихкоду
     */
    Product findByBarcode(String barcode);
    
    /**
     * Поиск товаров по местоположению
     */
    List<Product> findByLocationContainingIgnoreCase(String location);
    
    /**
     * Получить все категории
     */
    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.category IS NOT NULL ORDER BY p.category")
    List<String> findAllCategories();
    
    /**
     * Получить всех поставщиков
     */
    @Query("SELECT DISTINCT p.supplier FROM Product p WHERE p.supplier IS NOT NULL ORDER BY p.supplier")
    List<String> findAllSuppliers();
    
    /**
     * Статистика по категориям
     */
    @Query("SELECT p.category, COUNT(p), SUM(p.quantity), SUM(p.quantity * p.price) FROM Product p WHERE p.category IS NOT NULL GROUP BY p.category")
    List<Object[]> getCategoryStatistics();
    
    /**
     * Топ товаров по стоимости
     */
    @Query("SELECT p FROM Product p ORDER BY (p.quantity * p.price) DESC")
    List<Product> findTopValueProducts();
}