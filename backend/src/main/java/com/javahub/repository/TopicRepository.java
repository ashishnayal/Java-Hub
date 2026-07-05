package com.javahub.repository;

import com.javahub.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    Optional<Topic> findBySlug(String slug);
    List<Topic> findByCategory(String category);
    List<Topic> findByDifficulty(String difficulty);
    List<Topic> findByTitleContainingIgnoreCase(String keyword);
}
