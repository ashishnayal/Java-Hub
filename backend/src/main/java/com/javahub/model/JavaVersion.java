package com.javahub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "java_versions")
public class JavaVersion {

    @Id
    private String id;
    private int versionNumber;
    private String releaseName;
    private String releaseDate;
    private boolean lts;
    private String codename;
    private String description;
    private List<String> keyFeatures;
    private List<CodeExample> codeExamples;
}
