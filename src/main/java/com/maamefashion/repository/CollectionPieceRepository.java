package com.maamefashion.repository;

import com.maamefashion.model.Collection;
import com.maamefashion.model.CollectionPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionPieceRepository extends JpaRepository<CollectionPiece, String> {

    List<CollectionPiece> findByCollectionIdAndIsVisibleTrue(String collectionId);
    List<CollectionPiece> findByCollectionId(String collectionId);
}