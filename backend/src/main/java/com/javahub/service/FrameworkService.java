package com.javahub.service;

import com.javahub.model.Framework;
import com.javahub.repository.FrameworkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FrameworkService {

    private final FrameworkRepository repository;

    public List<Framework> getAllFrameworks() {
        return repository.findAll();
    }

    public Optional<Framework> getFrameworkBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    public List<Framework> getFrameworksByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Framework> searchFrameworks(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    public Framework createFramework(Framework framework) {
        return repository.save(framework);
    }

    public long count() {
        return repository.count();
    }
}
