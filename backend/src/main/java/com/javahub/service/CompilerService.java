package com.javahub.service;

import com.javahub.model.CompileRequest;
import com.javahub.model.CompileResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class CompilerService {

    public CompileResponse compileAndRun(CompileRequest request) {
        String code = request.getCode();
        
        // Basic heuristic to wrap code in a class and main method if missing
        if (!code.contains("class ")) {
            code = "public class Main {\n" +
                   "    public static void main(String[] args) throws Exception {\n" +
                   "        " + code.replace("\n", "\n        ") + "\n" +
                   "    }\n" +
                   "}";
        }
        
        // Extract class name (prefer public class, fallback to first class)
        String className = "Main";
        java.util.regex.Matcher publicMatcher = Pattern.compile("public\\s+class\\s+([A-Za-z0-9_]+)").matcher(code);
        if (publicMatcher.find()) {
            className = publicMatcher.group(1);
        } else {
            java.util.regex.Matcher m = Pattern.compile("class\\s+([A-Za-z0-9_]+)").matcher(code);
            if (m.find()) {
                className = m.group(1);
            }
        }

        try {
            // Create a temporary directory
            Path tempDir = Files.createTempDirectory("javahub-compile-" + UUID.randomUUID().toString());
            File tempDirFile = tempDir.toFile();
            tempDirFile.deleteOnExit();

            // Write the source file
            Path sourceFile = tempDir.resolve(className + ".java");
            Files.writeString(sourceFile, code);

            // Compile the code
            ProcessBuilder javacBuilder = new ProcessBuilder("javac", sourceFile.getFileName().toString());
            javacBuilder.directory(tempDirFile);
            Process javacProcess = javacBuilder.start();
            
            String compileError = readOutput(javacProcess.getErrorStream());
            int compileExitCode = javacProcess.waitFor();
            
            if (compileExitCode != 0) {
                return new CompileResponse("", "Compilation Error:\n" + compileError, compileExitCode);
            }

            // Run the code
            ProcessBuilder javaBuilder = new ProcessBuilder("java", className);
            javaBuilder.directory(tempDirFile);
            Process javaProcess = javaBuilder.start();

            // Wait for max 5 seconds
            boolean finished = javaProcess.waitFor(5, TimeUnit.SECONDS);
            
            if (!finished) {
                javaProcess.destroyForcibly();
                return new CompileResponse("", "Execution timed out after 5 seconds.", -1);
            }
 
            String runOutput = readOutput(javaProcess.getInputStream());
            String runError = readOutput(javaProcess.getErrorStream());
            int runExitCode = javaProcess.exitValue();

            return new CompileResponse(runOutput, runError, runExitCode);

        } catch (Exception e) {
            return new CompileResponse("", "Internal System Error: " + e.getMessage(), -1);
        }
    }

    private String readOutput(java.io.InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}
