package com.javahub.service;

import com.javahub.model.Topic;
import com.javahub.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository repository;

    public List<Topic> getAllTopics() {
        return repository.findAll();
    }

    public Optional<Topic> getTopicBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public List<Topic> getTopicsByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Topic> getTopicsByDifficulty(String difficulty) {
        return repository.findByDifficulty(difficulty);
    }

    public List<Topic> searchTopics(String keyword) {
        return repository.findByTitleContainingIgnoreCase(keyword);
    }

    public Topic createTopic(Topic topic) {
        return repository.save(topic);
    }

    public long count() {
        return repository.count();
    }
}
