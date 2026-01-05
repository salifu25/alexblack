package com.maamefashion.controller;

import com.maamefashion.dto.PublicCollectionDTO;
import com.maamefashion.model.Category;
import com.maamefashion.service.CategoryService;
import com.maamefashion.service.CollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {

    private final CollectionService collectionService;
    private final CategoryService categoryService;

    @GetMapping("/collections")
    public ResponseEntity<List<PublicCollectionDTO>> getPublicCollections() {
        log.info("getPublicCollections");
        return ResponseEntity.ok(collectionService.getPublicCollections());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        log.info("getCategories");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}