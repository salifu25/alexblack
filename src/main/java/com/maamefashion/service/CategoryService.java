package com.maamefashion.service;

import com.maamefashion.model.Category;
import com.maamefashion.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category createCategory(Category category) {
        if (category.getId() == null) {
            category.setId(UUID.randomUUID().toString());
        }
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(String id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryDetails.getName());

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(String id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }
    }
}