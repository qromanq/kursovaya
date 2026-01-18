package com.inventory.controller;

import com.inventory.model.Product;
import com.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST контроллер для работы с товарами
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * Получить все товары
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    /**
     * Получить товар по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Создать новый товар
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
    
    /**
     * Обновить товар
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> productOpt = productService.getProductById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(productDetails.getName());
            product.setQuantity(productDetails.getQuantity());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setSupplier(productDetails.getSupplier());
            product.setMinStock(productDetails.getMinStock());
            product.setDescription(productDetails.getDescription());
            product.setBarcode(productDetails.getBarcode());
            product.setLocation(productDetails.getLocation());
            product.setStatus(productDetails.getStatus());
            return ResponseEntity.ok(productService.saveProduct(product));
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Удалить товар
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.getProductById(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Поиск товаров
     */
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }
    
    /**
     * Товары с низким запасом
     */
    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return productService.getLowStockProducts();
    }
    
    /**
     * Товары без запаса
     */
    @GetMapping("/out-of-stock")
    public List<Product> getOutOfStockProducts() {
        return productService.getOutOfStockProducts();
    }
    
    /**
     * Поиск по категории
     */
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }
    
    /**
     * Поиск по поставщику
     */
    @GetMapping("/supplier")
    public List<Product> getProductsBySupplier(@RequestParam String supplier) {
        return productService.getProductsBySupplier(supplier);
    }
    
    /**
     * Поиск по статусу
     */
    @GetMapping("/status/{status}")
    public List<Product> getProductsByStatus(@PathVariable Product.ProductStatus status) {
        return productService.getProductsByStatus(status);
    }
    
    /**
     * Поиск по штрихкоду
     */
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<Product> getProductByBarcode(@PathVariable String barcode) {
        Product product = productService.getProductByBarcode(barcode);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
    
    /**
     * Поиск по местоположению
     */
    @GetMapping("/location")
    public List<Product> getProductsByLocation(@RequestParam String location) {
        return productService.getProductsByLocation(location);
    }
    
    /**
     * Получить все категории
     */
    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }
    
    /**
     * Получить всех поставщиков
     */
    @GetMapping("/suppliers")
    public List<String> getAllSuppliers() {
        return productService.getAllSuppliers();
    }
    
    /**
     * Обновить количество товара
     */
    @PatchMapping("/{id}/quantity")
    public ResponseEntity<Product> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        Product product = productService.updateQuantity(id, quantity);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
    
    /**
     * Добавить к запасу
     */
    @PatchMapping("/{id}/add-stock")
    public ResponseEntity<Product> addToStock(@PathVariable Long id, @RequestParam Integer quantity) {
        Product product = productService.addToStock(id, quantity);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
    
    /**
     * Убрать из запаса
     */
    @PatchMapping("/{id}/remove-stock")
    public ResponseEntity<Product> removeFromStock(@PathVariable Long id, @RequestParam Integer quantity) {
        Product product = productService.removeFromStock(id, quantity);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }
    
    /**
     * Получить статистику
     */
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        return productService.getStatistics();
    }
    
    /**
     * Топ товаров по стоимости
     */
    @GetMapping("/top-value")
    public List<Product> getTopValueProducts() {
        return productService.getTopValueProducts();
    }
}