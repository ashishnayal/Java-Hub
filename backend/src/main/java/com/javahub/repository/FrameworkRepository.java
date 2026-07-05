package com.javahub.repository;

import com.javahub.model.Framework;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrameworkRepository extends MongoRepository<Framework, String> {
    Optional<Framework> findBySlug(String slug);
    List<Framework> findByCategory(String category);
    List<Framework> findByNameContainingIgnoreCase(String keyword);
}
