package com.javahub.controller;

import com.javahub.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public Map<String, Object> search(@RequestParam String q) {
        return searchService.globalSearch(q);
    }
}
