package com.example.collectionsApp.repositories;

import com.example.collectionsApp.models.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<CollectionItem, Long> {

    List<CollectionItem> findByUser_id(Long id);
    Optional<CollectionItem> findById(Long id);
    void deleteById(Long id);
}
