package com.bytebandits.fintrackbackend.controller;

import com.bytebandits.fintrackbackend.dto.AllCategoriesResponseDTO;
import com.bytebandits.fintrackbackend.dto.CategoryDTO;
import com.bytebandits.fintrackbackend.dto.CategoryResponseDTO;
import com.bytebandits.fintrackbackend.model.Category;
import com.bytebandits.fintrackbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category =  categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(category.getId().toString());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AllCategoriesResponseDTO>> getAllCategories(@PathVariable UUID userId) {
        List<Category> allCategoriesList = categoryService.getAllCategories();
        List<AllCategoriesResponseDTO> allCategoriesResponse = new ArrayList<>();

        for (Category category: allCategoriesList) {
            AllCategoriesResponseDTO response = new AllCategoriesResponseDTO(category.getId(), category.getName(), category.getTransactionType());
            allCategoriesResponse.add(response);
        }
        return ResponseEntity.ok(allCategoriesResponse);
    }

    @GetMapping("/{userId}/{transactionType}")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoryNamesByUserId(@PathVariable UUID userId, @PathVariable String transactionType) {
        List<Category> categoryList = categoryService.getCategoryByUserId(userId);
        List<CategoryResponseDTO> categoryResponses = new ArrayList<>();

        for (Category category : categoryList) {
            if (category.getTransactionType().equals(transactionType)) {
                CategoryResponseDTO response = new CategoryResponseDTO(category.getId(), category.getName());
                categoryResponses.add(response);
            }
        }
        return ResponseEntity.ok(categoryResponses);
    }
}