package com.javahub.service;

import com.javahub.model.JavaVersion;
import com.javahub.repository.JavaVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JavaVersionService {

    private final JavaVersionRepository repository;

    public List<JavaVersion> getAllVersions() {
        List<JavaVersion> versions = repository.findAll();
        versions.sort((a, b) -> Integer.compare(a.getVersionNumber(), b.getVersionNumber()));
        return versions;
    }

    public Optional<JavaVersion> getVersionById(String id) {
        return repository.findById(id);
    }

    public Optional<JavaVersion> getVersionByNumber(int versionNumber) {
        return repository.findByVersionNumber(versionNumber);
    }

    public JavaVersion createVersion(JavaVersion version) {
        return repository.save(version);
    }

    public long count() {
        return repository.count();
    }
}
