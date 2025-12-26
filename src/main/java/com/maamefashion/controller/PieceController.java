package com.maamefashion.controller;

import com.maamefashion.dto.CollectionPieceDTO;
import com.maamefashion.service.PieceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pieces")
@RequiredArgsConstructor
public class PieceController {

    private final PieceService pieceService;

    @GetMapping("/collection/{collectionId}")
    public ResponseEntity<List<CollectionPieceDTO>> getPiecesByCollection(@PathVariable String collectionId) {
        return ResponseEntity.ok(pieceService.getPiecesByCollection(collectionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionPieceDTO> getPieceById(@PathVariable String id) {
        return ResponseEntity.ok(pieceService.getPieceById(id));
    }

    @PostMapping
    public ResponseEntity<CollectionPieceDTO> createPiece(@Valid @RequestBody CollectionPieceDTO pieceDTO) {
        return ResponseEntity.ok(pieceService.createPiece(pieceDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionPieceDTO> updatePiece(
            @PathVariable String id,
            @Valid @RequestBody CollectionPieceDTO pieceDTO) {
        return ResponseEntity.ok(pieceService.updatePiece(id, pieceDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePiece(@PathVariable String id) {
        pieceService.deletePiece(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/pricing")
    public ResponseEntity<CollectionPieceDTO> updatePricing(
            @PathVariable String id,
            @RequestBody PricingRequest request) {
        return ResponseEntity.ok(pieceService.updatePricing(id, request.price(), request.showPrice()));
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<CollectionPieceDTO> updateAvailability(
            @PathVariable String id,
            @RequestBody AvailabilityRequest request) {
        return ResponseEntity.ok(pieceService.updateAvailability(id, request.available()));
    }

    public record PricingRequest(String price, Boolean showPrice) {}
    public record AvailabilityRequest(Boolean available) {}
}