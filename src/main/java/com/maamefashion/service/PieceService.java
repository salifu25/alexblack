package com.maamefashion.service;

import com.maamefashion.dto.CollectionPieceDTO;
import com.maamefashion.model.Collection;
import com.maamefashion.model.CollectionPiece;
import com.maamefashion.repository.CollectionPieceRepository;
import com.maamefashion.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PieceService {

    private final CollectionPieceRepository pieceRepository;
    private final CollectionRepository collectionRepository;

    @Transactional(readOnly = true)
    public List<CollectionPieceDTO> getPiecesByCollection(String collectionId) {
        return pieceRepository.findByCollectionId(collectionId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CollectionPieceDTO getPieceById(String id) {
        CollectionPiece piece = pieceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piece not found"));
        return toDTO(piece);
    }

    @Transactional
    public CollectionPieceDTO createPiece(CollectionPieceDTO pieceDTO) {
        Collection collection = collectionRepository.findById(pieceDTO.getCollectionId())
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        CollectionPiece piece = CollectionPiece.builder()
                .collection(collection)
                .name(pieceDTO.getName())
                .description(pieceDTO.getDescription())
                .image(pieceDTO.getImage())
                .price(pieceDTO.getPrice())
                .showPrice(pieceDTO.getShowPrice() != null ? pieceDTO.getShowPrice() : false)
                .available(pieceDTO.getAvailable() != null ? pieceDTO.getAvailable() : true)
                .isVisible(pieceDTO.getIsVisible() != null ? pieceDTO.getIsVisible() : true)
                .category(pieceDTO.getCategory())
                .build();

        piece = pieceRepository.save(piece);
        return toDTO(piece);
    }

    @Transactional
    public CollectionPieceDTO updatePiece(String id, CollectionPieceDTO pieceDTO) {
        CollectionPiece piece = pieceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piece not found"));

        piece.setName(pieceDTO.getName());
        piece.setDescription(pieceDTO.getDescription());
        piece.setImage(pieceDTO.getImage());
        piece.setPrice(pieceDTO.getPrice());

        if (pieceDTO.getShowPrice() != null) {
            piece.setShowPrice(pieceDTO.getShowPrice());
        }

        if (pieceDTO.getAvailable() != null) {
            piece.setAvailable(pieceDTO.getAvailable());
        }

        if (pieceDTO.getIsVisible() != null) {
            piece.setIsVisible(pieceDTO.getIsVisible());
        }

        if (pieceDTO.getCategory() != null) {
            piece.setCategory(pieceDTO.getCategory());
        }

        piece = pieceRepository.save(piece);
        return toDTO(piece);
    }

    @Transactional
    public void deletePiece(String id) {
        pieceRepository.deleteById(id);
    }

    @Transactional
    public CollectionPieceDTO updatePricing(String id, String price, Boolean showPrice) {
        CollectionPiece piece = pieceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piece not found"));

        piece.setPrice(price);
        if (showPrice != null) {
            piece.setShowPrice(showPrice);
        }

        piece = pieceRepository.save(piece);
        return toDTO(piece);
    }

    @Transactional
    public CollectionPieceDTO updateAvailability(String id, Boolean available) {
        CollectionPiece piece = pieceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piece not found"));

        piece.setAvailable(available);
        piece = pieceRepository.save(piece);
        return toDTO(piece);
    }

    private CollectionPieceDTO toDTO(CollectionPiece piece) {
        return CollectionPieceDTO.builder()
                .id(piece.getId())
                .collectionId(piece.getCollection().getId())
                .name(piece.getName())
                .description(piece.getDescription())
                .image(piece.getImage())
                .price(piece.getPrice())
                .showPrice(piece.getShowPrice())
                .available(piece.getAvailable())
                .isVisible(piece.getIsVisible())
                .category(piece.getCategory())
                .createdAt(piece.getCreatedAt())
                .updatedAt(piece.getUpdatedAt())
                .build();
    }
}