package com.javahub.controller;

import com.javahub.model.JavaVersion;
import com.javahub.service.JavaVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versions")
@RequiredArgsConstructor
public class JavaVersionController {

    private final JavaVersionService service;

    @GetMapping
    public List<JavaVersion> getAllVersions() {
        return service.getAllVersions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JavaVersion> getVersionById(@PathVariable String id) {
        return service.getVersionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/number/{versionNumber}")
    public ResponseEntity<JavaVersion> getVersionByNumber(@PathVariable int versionNumber) {
        return service.getVersionByNumber(versionNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
