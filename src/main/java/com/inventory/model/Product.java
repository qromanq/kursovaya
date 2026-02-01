package com.inventory.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Модель товара для системы управления запасами
 */
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer quantity = 0;
    
    @Column(nullable = false)
    private Integer price = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column
    private String category;
    
    @Column
    private String supplier;
    
    @Column(name = "min_stock")
    private Integer minStock = 5;
    
    @Column
    private String description;
    
    @Column
    private String barcode;
    
    @Column
    private String location;
    
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.IN_STOCK;
    
    public enum ProductStatus {
        IN_STOCK("В наличии"), 
        DISCONTINUED("Снят с производства"), 
        OUT_OF_STOCK("Нет в наличии");
        
        private final String displayName;
        
        ProductStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Конструкторы
    public Product() {}
    
    public Product(String name, Integer quantity, Integer price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    
    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public ProductStatus getStatus() { return status; }
    public void setStatus(ProductStatus status) { this.status = status; }
    
    public String getStatusDisplayName() {
        return status != null ? status.getDisplayName() : "Неизвестно";
    }
    
    // Вспомогательные методы
    public boolean isLowStock() {
        return quantity != null && minStock != null && quantity < minStock;
    }
    
    public boolean isOutOfStock() {
        return quantity == null || quantity <= 0;
    }
    
    public Integer getTotalValue() {
        return (quantity != null && price != null) ? quantity * price : 0;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", supplier='" + supplier + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}