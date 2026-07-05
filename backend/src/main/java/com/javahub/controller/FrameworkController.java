package com.javahub.controller;

import com.javahub.model.Framework;
import com.javahub.service.FrameworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/frameworks")
@RequiredArgsConstructor
public class FrameworkController {

    private final FrameworkService service;

    @GetMapping
    public List<Framework> getAllFrameworks(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return service.getFrameworksByCategory(category);
        }
        return service.getAllFrameworks();
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Framework> getFrameworkBySlug(@PathVariable String slug) {
        return service.getFrameworkBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
