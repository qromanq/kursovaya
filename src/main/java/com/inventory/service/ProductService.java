package com.inventory.service;

import com.inventory.model.Product;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

/**
 * Сервис для работы с товарами
 */
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Получить все товары
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Получить товар по ID
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    /**
     * Сохранить товар
     */
    public Product saveProduct(Product product) {
        if (product.getId() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    
    /**
     * Удалить товар
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    /**
     * Поиск товаров по названию
     */
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Получить товары с низким запасом
     */
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }
    
    /**
     * Получить товары без запаса
     */
    public List<Product> getOutOfStockProducts() {
        return productRepository.findByQuantityLessThanEqual(0);
    }
    
    /**
     * Поиск по категории
     */
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category);
    }
    
    /**
     * Поиск по поставщику
     */
    public List<Product> getProductsBySupplier(String supplier) {
        return productRepository.findBySupplierContainingIgnoreCase(supplier);
    }
    
    /**
     * Поиск по статусу
     */
    public List<Product> getProductsByStatus(Product.ProductStatus status) {
        return productRepository.findByStatus(status);
    }
    
    /**
     * Поиск по штрихкоду
     */
    public Product getProductByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }
    
    /**
     * Поиск по местоположению
     */
    public List<Product> getProductsByLocation(String location) {
        return productRepository.findByLocationContainingIgnoreCase(location);
    }
    
    /**
     * Получить все категории
     */
    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }
    
    /**
     * Получить всех поставщиков
     */
    public List<String> getAllSuppliers() {
        return productRepository.findAllSuppliers();
    }
    
    /**
     * Обновить количество товара
     */
    public Product updateQuantity(Long id, Integer newQuantity) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setQuantity(newQuantity);
            product.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(product);
        }
        return null;
    }
    
    /**
     * Добавить к запасу
     */
    public Product addToStock(Long id, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setQuantity(product.getQuantity() + quantity);
            product.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(product);
        }
        return null;
    }
    
    /**
     * Убрать из запаса
     */
    public Product removeFromStock(Long id, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            int newQuantity = Math.max(0, product.getQuantity() - quantity);
            product.setQuantity(newQuantity);
            product.setUpdatedAt(LocalDateTime.now());
            return productRepository.save(product);
        }
        return null;
    }
    
    /**
     * Получить статистику
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        List<Product> allProducts = productRepository.findAll();
        
        stats.put("totalProducts", allProducts.size());
        stats.put("lowStockCount", getLowStockProducts().size());
        stats.put("outOfStockCount", getOutOfStockProducts().size());
        stats.put("totalValue", allProducts.stream()
                .mapToDouble(Product::getTotalValue)
                .sum());
        stats.put("averagePrice", allProducts.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0));
        stats.put("totalQuantity", allProducts.stream()
                .mapToInt(Product::getQuantity)
                .sum());
        
        return stats;
    }
    
    /**
     * Получить топ товаров по стоимости
     */
    public List<Product> getTopValueProducts() {
        return productRepository.findTopValueProducts();
    }
}