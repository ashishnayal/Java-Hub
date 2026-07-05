package com.javahub.controller;

import com.javahub.model.Topic;
import com.javahub.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService service;

    @GetMapping
    public List<Topic> getAllTopics(@RequestParam(required = false) String category,
                                   @RequestParam(required = false) String difficulty) {
        if (category != null && !category.isEmpty()) {
            return service.getTopicsByCategory(category);
        }
        if (difficulty != null && !difficulty.isEmpty()) {
            return service.getTopicsByDifficulty(difficulty);
        }
        return service.getAllTopics();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Topic> getTopicBySlug(@PathVariable String slug) {
        return service.getTopicBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
