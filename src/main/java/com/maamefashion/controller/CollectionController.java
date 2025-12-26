package com.maamefashion.controller;

import com.maamefashion.dto.CollectionDTO;
import com.maamefashion.service.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<CollectionDTO>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAllCollections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionDTO> getCollectionById(@PathVariable String id) {
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }

    @PostMapping
    public ResponseEntity<CollectionDTO> createCollection(@Valid @RequestBody CollectionDTO collectionDTO) {
        return ResponseEntity.ok(collectionService.createCollection(collectionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionDTO> updateCollection(
            @PathVariable String id,
            @Valid @RequestBody CollectionDTO collectionDTO) {
        return ResponseEntity.ok(collectionService.updateCollection(id, collectionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable String id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/visibility")
    public ResponseEntity<CollectionDTO> toggleVisibility(
            @PathVariable String id,
            @RequestBody ToggleVisibilityRequest request) {
        return ResponseEntity.ok(collectionService.toggleVisibility(id, request.isVisible()));
    }

//    @GetMapping("/{id}/pieces")
//    public ResponseEntity<List<com.maamefashion.dto.CollectionPieceDTO>> getCollectionPieces(@PathVariable String id) {
//        // This endpoint is handled by PieceController
//        return ResponseEntity.notFound().build();
//    }

    public record ToggleVisibilityRequest(boolean isVisible) {}
}