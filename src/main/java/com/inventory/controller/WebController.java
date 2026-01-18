package com.inventory.controller;

import com.inventory.model.Product;
import com.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Веб-контроллер для отображения страниц
 */
@Controller
public class WebController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * Главная страница
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("suppliers", productService.getAllSuppliers());
        model.addAttribute("statistics", productService.getStatistics());
        return "index";
    }
    
    /**
     * Страница аналитики
     */
    @GetMapping("/analytics")
    public String analytics(Model model) {
        Map<String, Object> stats = productService.getStatistics();
        model.addAttribute("statistics", stats);
        model.addAttribute("lowStockProducts", productService.getLowStockProducts());
        model.addAttribute("outOfStockProducts", productService.getOutOfStockProducts());
        model.addAttribute("topValueProducts", productService.getTopValueProducts());
        model.addAttribute("categories", productService.getAllCategories());
        return "analytics";
    }
    
    /**
     * Страница отчетов
     */
    @GetMapping("/reports")
    public String reports(Model model, 
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) String supplier) {
        
        if (category != null && !category.isEmpty()) {
            model.addAttribute("products", productService.getProductsByCategory(category));
            model.addAttribute("filterType", "Категория: " + category);
        } else if (supplier != null && !supplier.isEmpty()) {
            model.addAttribute("products", productService.getProductsBySupplier(supplier));
            model.addAttribute("filterType", "Поставщик: " + supplier);
        } else {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("filterType", "Все товары");
        }
        
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("suppliers", productService.getAllSuppliers());
        model.addAttribute("statistics", productService.getStatistics());
        
        return "reports";
    }
    
    /**
     * Страница управления запасами
     */
    @GetMapping("/inventory")
    public String inventory(Model model) {
        model.addAttribute("allProducts", productService.getAllProducts());
        model.addAttribute("lowStockProducts", productService.getLowStockProducts());
        model.addAttribute("outOfStockProducts", productService.getOutOfStockProducts());
        return "inventory";
    }
}