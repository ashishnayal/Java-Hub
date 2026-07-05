package com.javahub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeExample {
    private String title;
    private String language;
    private String code;
    private String explanation;
}
