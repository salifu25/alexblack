package com.maamefashion.service;

import com.maamefashion.dto.CollectionDTO;
import com.maamefashion.dto.PublicCollectionDTO;
import com.maamefashion.dto.PublicPieceDTO;
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
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionPieceRepository pieceRepository;

    @Transactional(readOnly = true)
    public List<PublicCollectionDTO> getPublicCollections() {
        return collectionRepository.findByIsVisibleTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::toPublicCollectionDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CollectionDTO> getAllCollections() {
        return collectionRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toCollectionDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CollectionDTO getCollectionById(String id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
        return toCollectionDTO(collection);
    }

    @Transactional
    public CollectionDTO createCollection(CollectionDTO collectionDTO) {
        Collection collection = Collection.builder()
                .name(collectionDTO.getName())
                .description(collectionDTO.getDescription())
                .season(collectionDTO.getSeason())
                .year(collectionDTO.getYear())
                .coverImage(collectionDTO.getCoverImage())
                .isVisible(collectionDTO.getIsVisible() != null ? collectionDTO.getIsVisible() : true)
                .build();

        collection = collectionRepository.save(collection);
        return toCollectionDTO(collection);
    }

    @Transactional
    public CollectionDTO updateCollection(String id, CollectionDTO collectionDTO) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        collection.setName(collectionDTO.getName());
        collection.setDescription(collectionDTO.getDescription());
        collection.setSeason(collectionDTO.getSeason());
        collection.setYear(collectionDTO.getYear());
        collection.setCoverImage(collectionDTO.getCoverImage());

        if (collectionDTO.getIsVisible() != null) {
            collection.setIsVisible(collectionDTO.getIsVisible());
        }

        collection = collectionRepository.save(collection);
        return toCollectionDTO(collection);
    }

    @Transactional
    public void deleteCollection(String id) {
        collectionRepository.deleteById(id);
    }

    @Transactional
    public CollectionDTO toggleVisibility(String id, boolean isVisible) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        collection.setIsVisible(isVisible);
        collection = collectionRepository.save(collection);
        return toCollectionDTO(collection);
    }

    private CollectionDTO toCollectionDTO(Collection collection) {
        List<CollectionPiece> pieces = pieceRepository.findByCollectionId(collection.getId());

        return CollectionDTO.builder()
                .id(collection.getId())
                .name(collection.getName())
                .description(collection.getDescription())
                .season(collection.getSeason())
                .year(collection.getYear())
                .coverImage(collection.getCoverImage())
                .isVisible(collection.getIsVisible())
                .createdAt(collection.getCreatedAt())
                .updatedAt(collection.getUpdatedAt())
                .pieces(pieces.stream()
                        .map(this::toCollectionPieceDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private com.maamefashion.dto.CollectionPieceDTO toCollectionPieceDTO(CollectionPiece piece) {
        return com.maamefashion.dto.CollectionPieceDTO.builder()
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

    private PublicCollectionDTO toPublicCollectionDTO(Collection collection) {
        List<CollectionPiece> visiblePieces = pieceRepository.findByCollectionIdAndIsVisibleTrue(collection.getId());

        return PublicCollectionDTO.builder()
                .id(collection.getId())
                .name(collection.getName())
                .season(collection.getSeason())
                .year(collection.getYear())
                .description(collection.getDescription())
                .coverImage(collection.getCoverImage())
                .pieces(visiblePieces.stream()
                        .filter(CollectionPiece::getAvailable)
                        .map(this::toPublicPieceDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    private PublicPieceDTO toPublicPieceDTO(CollectionPiece piece) {
        return PublicPieceDTO.builder()
                .id(piece.getId())
                .name(piece.getName())
                .description(piece.getDescription())
                .image(piece.getImage())
                .price(piece.getPrice())
                .available(piece.getAvailable())
                .category(piece.getCategory())
                .build();
    }
}