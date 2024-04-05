package com.bytebandits.fintrackbackend.service;

import com.bytebandits.fintrackbackend.dto.CategoryDTO;
import com.bytebandits.fintrackbackend.model.Category;
import com.bytebandits.fintrackbackend.model.User;
import com.bytebandits.fintrackbackend.repository.CategoryRepository;
import com.bytebandits.fintrackbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        User user = userRepository.findById(categoryDTO.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Category category = new Category();
        category.setUser(user);
        category.setName(categoryDTO.categoryName());
        category.setTransactionType(categoryDTO.transactionType());
        categoryRepository.save(category);
        return category;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getCategoryByUserId(UUID userId) {
        List<Category> categories = new ArrayList<>();
        for (Category category : getAllCategories()) {
            if ((category.getUser().getId().equals(userId))) {
                categories.add(category);
            }
        }
        return categories;
    }
}