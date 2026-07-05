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
@Document(collection = "frameworks")
public class Framework {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String slug;

    private String category; // Web, ORM, Testing, Build, Messaging, Security, Logging
    private String description;
    private String overview; // Detailed markdown overview
    private List<String> keyFeatures;
    private String gettingStarted; // Markdown getting started guide
    private List<CodeExample> codeExamples;
    private List<String> useCases;
    private String officialUrl;
}
