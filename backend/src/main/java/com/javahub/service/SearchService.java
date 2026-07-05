package com.javahub.service;

import com.javahub.model.Framework;
import com.javahub.model.JavaVersion;
import com.javahub.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final JavaVersionService versionService;
    private final TopicService topicService;
    private final FrameworkService frameworkService;

    public Map<String, Object> globalSearch(String query) {
        Map<String, Object> results = new HashMap<>();

        if (query == null || query.trim().isEmpty()) {
            results.put("versions", List.of());
            results.put("topics", List.of());
            results.put("frameworks", List.of());
            return results;
        }

        String q = query.trim().toLowerCase();

        // Search versions
        List<JavaVersion> versions = versionService.getAllVersions().stream()
                .filter(v -> v.getReleaseName().toLowerCase().contains(q)
                        || v.getDescription().toLowerCase().contains(q)
                        || v.getKeyFeatures().stream().anyMatch(f -> f.toLowerCase().contains(q))
                        || String.valueOf(v.getVersionNumber()).contains(q))
                .collect(Collectors.toList());

        // Search topics
        List<Topic> topics = topicService.getAllTopics().stream()
                .filter(t -> t.getTitle().toLowerCase().contains(q)
                        || t.getDescription().toLowerCase().contains(q)
                        || t.getCategory().toLowerCase().contains(q))
                .collect(Collectors.toList());

        // Search frameworks
        List<Framework> frameworks = frameworkService.getAllFrameworks().stream()
                .filter(f -> f.getName().toLowerCase().contains(q)
                        || f.getDescription().toLowerCase().contains(q)
                        || f.getCategory().toLowerCase().contains(q))
                .collect(Collectors.toList());

        results.put("versions", versions);
        results.put("topics", topics);
        results.put("frameworks", frameworks);
        results.put("totalResults", versions.size() + topics.size() + frameworks.size());

        return results;
    }
}
