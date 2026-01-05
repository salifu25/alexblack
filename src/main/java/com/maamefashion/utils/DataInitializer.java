package com.maamefashion.utils;

import com.maamefashion.model.Category;
import com.maamefashion.repository.CategoryRepository;
import com.maamefashion.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        // Initialize admin user
        userService.initAdminUser();

        // Initialize categories if empty
        if (categoryRepository.count() == 0) {
            List<Category> categories = Arrays.asList(
                    Category.builder()
                            .id("all")
                            .name("All")
                            .build(),
                    Category.builder()
                            .id("couture")
                            .name("Couture")
                            .build(),
                    Category.builder()
                            .id("ready-to-wear")
                            .name("Ready-to-Wear")
                            .build(),
                    Category.builder()
                            .id("accessories")
                            .name("Accessories")
                            .build()
            );
            categoryRepository.saveAll(categories);
        }
    }
}