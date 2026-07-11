package com.javahub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompileResponse {
    private String output;
    private String error;
    private int exitCode;
}
