package com.javahub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "topics")
public class Topic {

    @Id
    private String id;
    private String title;

    @Indexed(unique = true)
    private String slug;

    private String category; // Core, OOP, Advanced, Concurrency, Collections, etc.
    private String description;
    private String content; // Rich markdown content
    private List<CodeExample> codeExamples;
    private List<InterviewQuestion> interviewQuestions;
    private String difficulty; // Beginner, Intermediate, Advanced
    private List<String> tags;
}
