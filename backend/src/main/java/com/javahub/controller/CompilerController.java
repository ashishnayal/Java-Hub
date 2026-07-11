package com.javahub.controller;

import com.javahub.model.CompileRequest;
import com.javahub.model.CompileResponse;
import com.javahub.service.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compile")
@CrossOrigin(origins = "*") // Allows requests from Next.js frontend
public class CompilerController {

    @Autowired
    private CompilerService compilerService;

    @PostMapping
    public ResponseEntity<CompileResponse> compileCode(@RequestBody CompileRequest request) {
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new CompileResponse("", "Code cannot be empty", -1));
        }
        CompileResponse response = compilerService.compileAndRun(request);
        return ResponseEntity.ok(response);
    }
}
