package com.javahub.config;

import com.javahub.model.CodeExample;
import com.javahub.model.Framework;
import com.javahub.model.JavaVersion;
import com.javahub.model.Topic;
import com.javahub.repository.FrameworkRepository;
import com.javahub.repository.JavaVersionRepository;
import com.javahub.repository.TopicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.javahub.repository.FrameworkRepository;
import com.javahub.repository.JavaVersionRepository;
import com.javahub.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final JavaVersionRepository versionRepo;
    private final TopicRepository topicRepo;
    private final FrameworkRepository frameworkRepo;

    @Override
    public void run(String... args) {
        if (versionRepo.count() == 0) {
            log.info("Seeding Java Versions...");
            seedVersions();
        }
        log.info("Seeding Topics...");
        topicRepo.deleteAll();
        seedTopics();
        if (frameworkRepo.count() == 0) {
            log.info("Seeding Frameworks...");
            seedFrameworks();
        }
        log.info("Data seeding complete! Versions: {}, Topics: {}, Frameworks: {}",
                versionRepo.count(), topicRepo.count(), frameworkRepo.count());
    }

    private void seedVersions() {
        List<JavaVersion> versions = List.of(
            createVersion(1, "JDK 1.0", "January 23, 1996", false, "Oak",
                "The initial release of Java, originally called Oak, developed by James Gosling at Sun Microsystems. It introduced the Java Virtual Machine (JVM) and the core concept of 'Write Once, Run Anywhere' (WORA).",
                List.of("Java Virtual Machine (JVM)", "Applets for web browsers", "AWT (Abstract Window Toolkit)", "Basic I/O and Networking", "Garbage Collection", "Multi-threading support"),
                List.of(new CodeExample("Hello World - The Beginning", "java",
                    "// The very first Java program pattern\npublic class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}",
                    "This classic program demonstrates the basic structure of a Java application — a public class with a main method as the entry point."))),

            createVersion(2, "JDK 1.1", "February 19, 1997", false, "",
                "JDK 1.1 introduced inner classes, JavaBeans, JDBC for database connectivity, RMI for distributed computing, and the Reflection API — foundational features that shaped Java's enterprise future.",
                List.of("Inner Classes", "JavaBeans component model", "JDBC (Java Database Connectivity)", "RMI (Remote Method Invocation)", "Reflection API", "Internationalization (i18n)"),
                List.of(new CodeExample("Inner Classes", "java",
                    "public class Outer {\n    private String message = \"Hello from Outer\";\n\n    class Inner {\n        void display() {\n            // Inner class can access outer class members\n            System.out.println(message);\n        }\n    }\n\n    public static void main(String[] args) {\n        Outer outer = new Outer();\n        Outer.Inner inner = outer.new Inner();\n        inner.display();\n    }\n}",
                    "Inner classes allow you to logically group classes and access the enclosing class's members, even private ones."))),

            createVersion(3, "J2SE 1.2", "December 8, 1998", false, "Playground",
                "Known as 'Java 2', this release was a massive leap. It introduced the Collections Framework, Swing GUI toolkit, and the JIT (Just-In-Time) compiler, tripling the size of the platform.",
                List.of("Collections Framework (List, Set, Map)", "Swing GUI Toolkit", "JIT (Just-In-Time) Compiler", "Java Plugin for browsers", "strictfp keyword", "Java IDL / CORBA support"),
                List.of(new CodeExample("Collections Framework", "java",
                    "import java.util.*;\n\npublic class CollectionsDemo {\n    public static void main(String[] args) {\n        // ArrayList - dynamic array\n        List<String> list = new ArrayList<>();\n        list.add(\"Java\");\n        list.add(\"Python\");\n        list.add(\"C++\");\n\n        // HashMap - key-value pairs\n        Map<String, Integer> map = new HashMap<>();\n        map.put(\"Java\", 1995);\n        map.put(\"Python\", 1991);\n\n        // Iterate\n        for (String lang : list) {\n            System.out.println(lang + \": \" + map.getOrDefault(lang, 0));\n        }\n    }\n}",
                    "The Collections Framework provided a unified architecture for storing, manipulating, and iterating over groups of objects."))),

            createVersion(4, "J2SE 1.3", "May 8, 2000", false, "Kestrel",
                "J2SE 1.3 focused on performance and maturity. It introduced the HotSpot JVM as the default, Java Sound API, and improvements to RMI and networking.",
                List.of("HotSpot JVM (default)", "Java Sound API", "JNDI (Java Naming and Directory Interface)", "RMI over IIOP", "Synthetic proxy classes", "Timer API"),
                List.of(new CodeExample("Timer API", "java",
                    "import java.util.Timer;\nimport java.util.TimerTask;\n\npublic class TimerDemo {\n    public static void main(String[] args) {\n        Timer timer = new Timer();\n        timer.schedule(new TimerTask() {\n            int count = 0;\n            @Override\n            public void run() {\n                System.out.println(\"Tick: \" + (++count));\n                if (count >= 5) timer.cancel();\n            }\n        }, 0, 1000); // delay=0, period=1000ms\n    }\n}",
                    "The Timer API allowed scheduling of tasks for future execution in a background thread."))),

            createVersion(5, "J2SE 1.4", "February 6, 2002", false, "Merlin",
                "Java 1.4 was a significant update bringing assertions, regular expressions, NIO (New I/O), logging API, and XML parsing. It was the first release under the Java Community Process (JCP).",
                List.of("Assertions (assert keyword)", "Regular Expressions (java.util.regex)", "NIO (New I/O) - java.nio", "Logging API (java.util.logging)", "XML Processing (JAXP)", "Chained Exceptions", "IPv6 support"),
                List.of(new CodeExample("Regular Expressions & NIO", "java",
                    "import java.util.regex.*;\nimport java.nio.*;\nimport java.nio.charset.*;\n\npublic class Java14Features {\n    public static void main(String[] args) {\n        // Regular Expressions\n        String email = \"dev@javahub.com\";\n        boolean valid = Pattern.matches(\n            \"^[A-Za-z0-9+_.-]+@(.+)$\", email);\n        System.out.println(\"Valid email: \" + valid);\n\n        // NIO Buffer\n        CharBuffer buffer = CharBuffer.allocate(64);\n        buffer.put(\"Hello NIO!\");\n        buffer.flip();\n        System.out.println(\"Buffer: \" + buffer.toString());\n    }\n}",
                    "Java 1.4 added powerful text matching with regex and high-performance I/O with the NIO package."))),

            createVersion(6, "Java SE 5.0", "September 30, 2004", false, "Tiger",
                "Java 5 was a REVOLUTIONARY release — arguably the biggest language change in Java's history. It introduced Generics, Annotations, Enums, Autoboxing, Varargs, the enhanced for-loop, and the java.util.concurrent package.",
                List.of("Generics", "Annotations (@Override, @Deprecated, etc.)", "Enumerations (enum)", "Autoboxing / Unboxing", "Varargs (variable arguments)", "Enhanced for-each loop", "Static imports", "java.util.concurrent package", "StringBuilder class"),
                List.of(
                    new CodeExample("Generics & Enhanced For Loop", "java",
                        "import java.util.*;\n\npublic class GenericsDemo {\n    // Generic method\n    public static <T extends Comparable<T>> T findMax(List<T> list) {\n        T max = list.get(0);\n        for (T item : list) {  // Enhanced for-loop\n            if (item.compareTo(max) > 0) {\n                max = item;\n            }\n        }\n        return max;\n    }\n\n    public static void main(String[] args) {\n        List<Integer> numbers = List.of(3, 7, 1, 9, 4);\n        System.out.println(\"Max: \" + findMax(numbers)); // 9\n\n        List<String> names = List.of(\"Alice\", \"Zara\", \"Bob\");\n        System.out.println(\"Max: \" + findMax(names)); // Zara\n    }\n}",
                        "Generics provide compile-time type safety, eliminating the need for explicit casting and catching type errors at compile time."),
                    new CodeExample("Enums & Annotations", "java",
                        "public enum Planet {\n    MERCURY(3.303e+23, 2.4397e6),\n    VENUS(4.869e+24, 6.0518e6),\n    EARTH(5.976e+24, 6.37814e6);\n\n    private final double mass;\n    private final double radius;\n\n    Planet(double mass, double radius) {\n        this.mass = mass;\n        this.radius = radius;\n    }\n\n    // Surface gravity in m/s²\n    double surfaceGravity() {\n        final double G = 6.67300E-11;\n        return G * mass / (radius * radius);\n    }\n\n    @Override\n    public String toString() {\n        return name() + \" (g=\" + \n            String.format(\"%.2f\", surfaceGravity()) + \" m/s²)\";\n    }\n}",
                        "Enums in Java are full-featured classes that can have fields, methods, and constructors — far more powerful than enums in C/C++."))),

            createVersion(7, "Java SE 6", "December 11, 2006", false, "Mustang",
                "Java 6 focused on performance improvements and developer productivity. It added scripting language support (JSR 223), improved web services, JDBC 4.0, and the Java Compiler API.",
                List.of("Scripting Language Support (JSR 223)", "JDBC 4.0", "Java Compiler API", "Pluggable Annotations (JSR 269)", "Improved Web Services (JAX-WS 2.0)", "Desktop API improvements", "Performance improvements (synchronization, GC)"),
                List.of(new CodeExample("Scripting Engine", "java",
                    "import javax.script.*;\n\npublic class ScriptingDemo {\n    public static void main(String[] args) throws Exception {\n        ScriptEngineManager manager = new ScriptEngineManager();\n        ScriptEngine engine = manager.getEngineByName(\"js\");\n\n        // Execute JavaScript from Java\n        engine.eval(\"print('Hello from JavaScript!')\");\n\n        // Pass variables between Java and script\n        engine.put(\"x\", 10);\n        engine.put(\"y\", 20);\n        Object result = engine.eval(\"x + y\");\n        System.out.println(\"Result: \" + result); // 30\n    }\n}",
                    "Java 6 introduced the javax.script package, allowing execution of scripting languages (JavaScript, Groovy, etc.) from within Java applications."))),

            createVersion(8, "Java SE 7", "July 28, 2011", false, "Dolphin",
                "Java 7 introduced the diamond operator, try-with-resources, multi-catch exceptions, strings in switch, and the Fork/Join framework. It also introduced NIO.2 for modern file system operations.",
                List.of("Diamond Operator (<>)", "Try-with-Resources (AutoCloseable)", "Multi-catch Exceptions", "Strings in Switch Statements", "Binary Literals & Underscores in Numerics", "Fork/Join Framework", "NIO.2 (java.nio.file)", "invokedynamic bytecode instruction"),
                List.of(
                    new CodeExample("Try-with-Resources & Diamond Operator", "java",
                        "import java.io.*;\nimport java.util.*;\n\npublic class Java7Features {\n    public static void main(String[] args) {\n        // Diamond operator - type inference\n        Map<String, List<String>> map = new HashMap<>();\n\n        // Try-with-resources - auto-close\n        try (BufferedReader reader = new BufferedReader(\n                new FileReader(\"data.txt\"))) {\n            String line;\n            while ((line = reader.readLine()) != null) {\n                System.out.println(line);\n            }\n        } catch (FileNotFoundException | IOException e) {\n            // Multi-catch exception\n            System.err.println(\"Error: \" + e.getMessage());\n        }\n\n        // Binary literals & underscores\n        int binary = 0b1010_1010;\n        long creditCard = 1234_5678_9012_3456L;\n    }\n}",
                        "Java 7 reduced boilerplate significantly: diamond operator eliminates redundant type parameters, try-with-resources ensures proper resource cleanup, and multi-catch simplifies exception handling."),
                    new CodeExample("NIO.2 File Operations", "java",
                        "import java.nio.file.*;\nimport java.nio.charset.StandardCharsets;\nimport java.util.List;\n\npublic class NIO2Demo {\n    public static void main(String[] args) throws Exception {\n        Path path = Paths.get(\"example.txt\");\n\n        // Write to file\n        Files.write(path, List.of(\"Hello\", \"NIO.2\"),\n            StandardCharsets.UTF_8);\n\n        // Read all lines\n        List<String> lines = Files.readAllLines(path);\n        lines.forEach(System.out::println);\n\n        // Walk directory tree\n        Files.walk(Paths.get(\".\"))\n            .filter(Files::isRegularFile)\n            .forEach(System.out::println);\n    }\n}",
                    "NIO.2 provided a modern, clean API for file system operations, replacing many uses of java.io.File."))),

            createVersion(9, "Java SE 8", "March 18, 2014", true, "",
                "Java 8 is one of the most important releases in Java history and the most widely used LTS version. It introduced Lambda Expressions, the Stream API, the new Date/Time API (java.time), Optional, and default methods in interfaces — fundamentally changing how Java code is written.",
                List.of("Lambda Expressions", "Stream API", "java.time (Date/Time API)", "Optional<T>", "Default Methods in Interfaces", "Method References (::)", "Functional Interfaces (@FunctionalInterface)", "Nashorn JavaScript Engine", "CompletableFuture"),
                List.of(
                    new CodeExample("Lambda Expressions & Streams", "java",
                        "import java.util.*;\nimport java.util.stream.*;\n\npublic class Java8Lambdas {\n    public static void main(String[] args) {\n        List<String> names = List.of(\n            \"Alice\", \"Bob\", \"Charlie\", \"Diana\", \"Eve\");\n\n        // Stream + Lambda: filter, transform, collect\n        List<String> result = names.stream()\n            .filter(name -> name.length() > 3)\n            .map(String::toUpperCase)\n            .sorted()\n            .collect(Collectors.toList());\n\n        System.out.println(result);\n        // [ALICE, CHARLIE, DIANA]\n\n        // Reduce\n        int totalLength = names.stream()\n            .mapToInt(String::length)\n            .sum();\n        System.out.println(\"Total chars: \" + totalLength);\n\n        // Grouping\n        Map<Integer, List<String>> byLength = names.stream()\n            .collect(Collectors.groupingBy(String::length));\n        System.out.println(byLength);\n    }\n}",
                        "Lambda expressions enable functional programming in Java. Combined with the Stream API, they provide a powerful, declarative way to process collections."),
                    new CodeExample("Optional & Date/Time API", "java",
                        "import java.util.Optional;\nimport java.time.*;\nimport java.time.format.DateTimeFormatter;\n\npublic class Java8Features {\n    public static void main(String[] args) {\n        // Optional - avoid NullPointerException\n        Optional<String> name = Optional.of(\"Java\");\n        String upper = name\n            .filter(n -> n.length() > 3)\n            .map(String::toUpperCase)\n            .orElse(\"UNKNOWN\");\n        System.out.println(upper); // JAVA\n\n        // New Date/Time API (immutable & thread-safe)\n        LocalDate today = LocalDate.now();\n        LocalDate javaRelease = LocalDate.of(1996, 1, 23);\n        Period age = Period.between(javaRelease, today);\n        System.out.println(\"Java is \" + age.getYears() + \" years old\");\n\n        // Formatting\n        LocalDateTime now = LocalDateTime.now();\n        String formatted = now.format(\n            DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\"));\n        System.out.println(\"Now: \" + formatted);\n    }\n}",
                        "Optional provides a container for potentially null values, and the java.time API finally gave Java a modern, immutable, thread-safe date/time library."))),

            createVersion(10, "Java SE 9", "September 21, 2017", false, "",
                "Java 9 introduced the Module System (Project Jigsaw) — the biggest structural change to the JDK. It also added JShell (REPL), private methods in interfaces, improved Streams, and the HTTP/2 Client (incubator).",
                List.of("Module System (Project Jigsaw)", "JShell (REPL)", "Private Methods in Interfaces", "Improved Stream API (takeWhile, dropWhile, ofNullable)", "Collection Factory Methods (List.of, Set.of, Map.of)", "HTTP/2 Client (Incubator)", "Multi-release JARs", "Process API improvements"),
                List.of(new CodeExample("Modules & Collection Factories", "java",
                    "import java.util.*;\nimport java.util.stream.Stream;\n\npublic class Java9Features {\n    public static void main(String[] args) {\n        // Immutable Collection Factories\n        List<String> list = List.of(\"a\", \"b\", \"c\");\n        Set<Integer> set = Set.of(1, 2, 3);\n        Map<String, Integer> map = Map.of(\n            \"Java\", 9, \"Kotlin\", 1);\n\n        // Stream improvements\n        Stream.of(1, 2, 3, 4, 5, 6, 7, 8)\n            .takeWhile(n -> n < 5)\n            .forEach(System.out::println); // 1,2,3,4\n\n        Stream.of(1, 2, 3, 4, 5)\n            .dropWhile(n -> n < 3)\n            .forEach(System.out::println); // 3,4,5\n\n        // Optional improvements\n        Optional.of(\"Hello\")\n            .ifPresentOrElse(\n                val -> System.out.println(\"Found: \" + val),\n                () -> System.out.println(\"Empty\"));\n    }\n}\n\n// module-info.java\n// module com.javahub.app {\n//     requires java.base;\n//     requires java.net.http;\n//     exports com.javahub.api;\n// }",
                    "Java 9's module system enables better encapsulation of JDK internals. The convenience factory methods for collections eliminate the need for verbose initialization."))),

            createVersion(11, "Java SE 10", "March 20, 2018", false, "",
                "Java 10 introduced Local Variable Type Inference with the `var` keyword — allowing the compiler to infer local variable types. It also brought application class-data sharing and experimental parallel full GC for G1.",
                List.of("Local Variable Type Inference (var)", "Application Class-Data Sharing", "Parallel Full GC for G1", "Thread-Local Handshakes", "Heap Allocation on Alternative Memory Devices", "Experimental JIT Compiler (Graal)"),
                List.of(new CodeExample("var - Local Variable Type Inference", "java",
                    "import java.util.*;\nimport java.util.stream.Collectors;\n\npublic class Java10Features {\n    public static void main(String[] args) {\n        // var infers the type from the right-hand side\n        var message = \"Hello Java 10\";   // String\n        var numbers = List.of(1, 2, 3, 4, 5); // List<Integer>\n        var map = new HashMap<String, List<Integer>>();\n\n        // Useful for complex generic types\n        var result = numbers.stream()\n            .filter(n -> n % 2 == 0)\n            .collect(Collectors.toList());\n\n        System.out.println(result); // [2, 4]\n\n        // Unmodifiable copy\n        var copy = List.copyOf(numbers);\n        System.out.println(copy);\n    }\n}",
                    "The var keyword reduces verbosity for local variables while maintaining full type safety — the type is still determined at compile time, not runtime."))),

            createVersion(12, "Java SE 11", "September 25, 2018", true, "",
                "Java 11 is a Long-Term Support release. It standardized the HTTP Client API, added String utility methods, introduced the `var` keyword for lambda parameters, and removed Java EE and CORBA modules from the JDK.",
                List.of("HTTP Client API (standardized)", "String new methods (isBlank, lines, strip, repeat)", "Local-Variable Syntax for Lambda Parameters", "Nest-Based Access Control", "Flight Recorder (open-sourced)", "Epsilon: No-Op Garbage Collector", "Single-File Source-Code Programs (java MyApp.java)"),
                List.of(new CodeExample("HTTP Client & New String Methods", "java",
                    "import java.net.URI;\nimport java.net.http.*;\nimport java.net.http.HttpResponse.BodyHandlers;\n\npublic class Java11Features {\n    public static void main(String[] args) throws Exception {\n        // New String methods\n        System.out.println(\" \".isBlank());        // true\n        System.out.println(\"Java\".repeat(3));     // JavaJavaJava\n        System.out.println(\" Hello \".strip());    // \"Hello\"\n\n        \"Line1\\nLine2\\nLine3\".lines()\n            .forEach(System.out::println);\n\n        // HTTP Client API\n        var client = HttpClient.newHttpClient();\n        var request = HttpRequest.newBuilder()\n            .uri(URI.create(\"https://api.github.com\"))\n            .header(\"Accept\", \"application/json\")\n            .GET()\n            .build();\n\n        var response = client.send(request,\n            BodyHandlers.ofString());\n        System.out.println(\"Status: \" + response.statusCode());\n    }\n}",
                    "Java 11's HTTP Client is modern, supports HTTP/2, WebSocket, and both sync/async operations. The new String methods solve common pain points."))),

            createVersion(13, "Java SE 12", "March 19, 2019", false, "",
                "Java 12 introduced Switch Expressions as a preview feature and the compact number formatting API. It also included improvements to the G1 garbage collector.",
                List.of("Switch Expressions (Preview)", "Compact Number Formatting", "Shenandoah GC (Experimental)", "JVM Constants API", "G1: Abortable Mixed Collections", "G1: Promptly Return Unused Memory"),
                List.of(new CodeExample("Switch Expressions (Preview)", "java",
                    "public class Java12Features {\n    public static void main(String[] args) {\n        // Switch Expression with arrow syntax\n        String day = \"MONDAY\";\n        String type = switch (day) {\n            case \"MONDAY\", \"TUESDAY\", \"WEDNESDAY\",\n                 \"THURSDAY\", \"FRIDAY\" -> \"Weekday\";\n            case \"SATURDAY\", \"SUNDAY\" -> \"Weekend\";\n            default -> \"Unknown\";\n        };\n        System.out.println(day + \" is a \" + type);\n\n        // Compact Number Formatting\n        var formatter = java.text.NumberFormat\n            .getCompactNumberInstance();\n        System.out.println(formatter.format(1000));    // 1K\n        System.out.println(formatter.format(1000000)); // 1M\n    }\n}",
                    "Switch expressions allow switch to return a value, and the arrow syntax eliminates fall-through bugs."))),

            createVersion(14, "Java SE 13", "September 17, 2019", false, "",
                "Java 13 introduced Text Blocks (preview) for multi-line string literals and continued refining switch expressions. It also reimplemented the legacy Socket API.",
                List.of("Text Blocks (Preview)", "Switch Expressions (Second Preview)", "Reimplemented Legacy Socket API", "ZGC: Uncommit Unused Memory", "Dynamic CDS Archives"),
                List.of(new CodeExample("Text Blocks", "java",
                    "public class Java13Features {\n    public static void main(String[] args) {\n        // Text Blocks - multi-line strings\n        String json = \"\"\"\n                {\n                    \"name\": \"Java Hub\",\n                    \"version\": 13,\n                    \"features\": [\n                        \"Text Blocks\",\n                        \"Switch Expressions\"\n                    ]\n                }\n                \"\"\";\n        System.out.println(json);\n\n        // HTML template\n        String html = \"\"\"\n                <html>\n                    <body>\n                        <h1>Hello, Text Blocks!</h1>\n                    </body>\n                </html>\n                \"\"\";\n        System.out.println(html);\n    }\n}",
                    "Text blocks eliminate the need for escape sequences and string concatenation when working with multi-line strings like JSON, HTML, or SQL."))),

            createVersion(15, "Java SE 14", "March 17, 2020", false, "",
                "Java 14 finalized Switch Expressions, introduced Records (preview) for immutable data classes, and added Pattern Matching for instanceof (preview). It also introduced helpful NullPointerExceptions.",
                List.of("Switch Expressions (Standard)", "Records (Preview)", "Pattern Matching for instanceof (Preview)", "Helpful NullPointerExceptions", "Text Blocks (Second Preview)", "Foreign Memory Access API (Incubator)", "Packaging Tool (jpackage) Incubator"),
                List.of(new CodeExample("Records & Pattern Matching", "java",
                    "// Record - immutable data carrier\nrecord Point(double x, double y) {\n    // Compact constructor for validation\n    Point {\n        if (x < 0 || y < 0)\n            throw new IllegalArgumentException(\"Negative!\");\n    }\n\n    double distanceTo(Point other) {\n        return Math.sqrt(\n            Math.pow(this.x - other.x, 2) +\n            Math.pow(this.y - other.y, 2));\n    }\n}\n\npublic class Java14Features {\n    // Pattern Matching for instanceof\n    static String describe(Object obj) {\n        if (obj instanceof String s) {\n            return \"String of length \" + s.length();\n        } else if (obj instanceof Integer i) {\n            return \"Integer: \" + i;\n        }\n        return \"Unknown\";\n    }\n\n    public static void main(String[] args) {\n        var p1 = new Point(3, 4);\n        var p2 = new Point(0, 0);\n        System.out.println(\"Distance: \" + p1.distanceTo(p2));\n        System.out.println(describe(\"hello\"));\n        System.out.println(describe(42));\n    }\n}",
                    "Records eliminate boilerplate for data classes (auto-generated constructor, getters, equals, hashCode, toString). Pattern matching simplifies instanceof checks."))),

            createVersion(16, "Java SE 15", "September 15, 2020", false, "",
                "Java 15 introduced Sealed Classes (preview), finalized Text Blocks, and added Hidden Classes. It also continued refining Records and Pattern Matching.",
                List.of("Sealed Classes (Preview)", "Text Blocks (Standard)", "Hidden Classes", "Records (Second Preview)", "Pattern Matching for instanceof (Second Preview)", "ZGC: Production Ready", "Shenandoah GC: Production Ready", "Edwards-Curve Digital Signature Algorithm"),
                List.of(new CodeExample("Sealed Classes & Text Blocks", "java",
                    "// Sealed class - restricts which classes can extend it\npublic sealed class Shape\n    permits Circle, Rectangle, Triangle {\n\n    abstract double area();\n}\n\nfinal class Circle extends Shape {\n    private final double radius;\n    Circle(double radius) { this.radius = radius; }\n    @Override double area() {\n        return Math.PI * radius * radius;\n    }\n}\n\nfinal class Rectangle extends Shape {\n    private final double w, h;\n    Rectangle(double w, double h) { this.w = w; this.h = h; }\n    @Override double area() { return w * h; }\n}\n\nnon-sealed class Triangle extends Shape {\n    private final double base, height;\n    Triangle(double base, double height) {\n        this.base = base; this.height = height;\n    }\n    @Override double area() { return 0.5 * base * height; }\n}",
                    "Sealed classes give you fine-grained control over inheritance hierarchies, enabling the compiler to verify exhaustive pattern matching."))),

            createVersion(17, "Java SE 16", "March 16, 2021", false, "",
                "Java 16 finalized Records and Pattern Matching for instanceof. It also introduced the Vector API (incubator) and the Foreign Linker API for native code interop.",
                List.of("Records (Standard)", "Pattern Matching for instanceof (Standard)", "Sealed Classes (Second Preview)", "Vector API (Incubator)", "Foreign Linker API (Incubator)", "Stream.toList() method", "Day Period Support in DateTimeFormatter"),
                List.of(new CodeExample("Records & Stream.toList()", "java",
                    "import java.util.List;\nimport java.util.stream.IntStream;\n\nrecord Employee(String name, String dept, double salary) {}\n\npublic class Java16Features {\n    public static void main(String[] args) {\n        var employees = List.of(\n            new Employee(\"Alice\", \"Engineering\", 95000),\n            new Employee(\"Bob\", \"Marketing\", 75000),\n            new Employee(\"Charlie\", \"Engineering\", 105000),\n            new Employee(\"Diana\", \"Marketing\", 82000)\n        );\n\n        // Stream.toList() - new convenience method\n        var engineers = employees.stream()\n            .filter(e -> e.dept().equals(\"Engineering\"))\n            .toList(); // Unmodifiable list\n\n        engineers.forEach(e ->\n            System.out.println(e.name() + \": $\" + e.salary()));\n    }\n}",
                    "Records are now standard! Stream.toList() provides a cleaner alternative to .collect(Collectors.toList())."))),

            createVersion(18, "Java SE 17", "September 14, 2021", true, "",
                "Java 17 is a major Long-Term Support (LTS) release. It finalized Sealed Classes, introduced Pattern Matching for Switch (preview), and deprecated the Security Manager. This is a widely adopted LTS version in enterprise.",
                List.of("Sealed Classes (Standard)", "Pattern Matching for Switch (Preview)", "Enhanced Pseudo-Random Number Generators", "Deprecate the Security Manager", "Foreign Function & Memory API (Incubator)", "Context-Specific Deserialization Filters", "Strong encapsulation of JDK internals"),
                List.of(new CodeExample("Sealed Classes & Pattern Matching for Switch", "java",
                    "sealed interface Shape permits Circle, Rectangle {}\nrecord Circle(double radius) implements Shape {}\nrecord Rectangle(double width, double height) implements Shape {}\n\npublic class Java17Features {\n    // Pattern matching for switch (preview)\n    static double area(Shape shape) {\n        return switch (shape) {\n            case Circle c -> Math.PI * c.radius() * c.radius();\n            case Rectangle r -> r.width() * r.height();\n        }; // Exhaustive! No default needed\n    }\n\n    static String describe(Object obj) {\n        return switch (obj) {\n            case Integer i -> \"int: \" + i;\n            case String s -> \"String: \" + s;\n            case null -> \"null value\";\n            default -> \"other: \" + obj;\n        };\n    }\n\n    public static void main(String[] args) {\n        System.out.println(area(new Circle(5)));\n        System.out.println(area(new Rectangle(4, 6)));\n        System.out.println(describe(\"hello\"));\n    }\n}",
                    "Sealed classes + pattern matching for switch enable exhaustive, type-safe switch expressions — the compiler ensures you handle all possible subtypes."))),

            createVersion(19, "Java SE 18", "March 22, 2022", false, "",
                "Java 18 made UTF-8 the default charset, introduced a simple web server for prototyping, and continued incubating the Foreign Function & Memory API.",
                List.of("UTF-8 by Default", "Simple Web Server (jwebserver)", "Code Snippets in Java API Documentation", "Pattern Matching for Switch (Second Preview)", "Foreign Function & Memory API (Second Incubator)", "Vector API (Third Incubator)", "Internet-Address Resolution SPI"),
                List.of(new CodeExample("Simple Web Server", "java",
                    "import com.sun.net.httpserver.SimpleFileServer;\nimport java.net.InetSocketAddress;\nimport java.nio.file.Path;\n\npublic class Java18Features {\n    public static void main(String[] args) {\n        // Start a simple file server from command line:\n        // $ jwebserver -p 8080 -d /path/to/files\n\n        // Or programmatically:\n        var server = SimpleFileServer.createFileServer(\n            new InetSocketAddress(8080),\n            Path.of(\".\"),\n            SimpleFileServer.OutputLevel.VERBOSE\n        );\n        server.start();\n        System.out.println(\"Server started on port 8080\");\n    }\n}",
                    "Java 18's built-in simple web server is perfect for prototyping and testing. UTF-8 as default eliminates charset-related bugs across platforms."))),

            createVersion(20, "Java SE 19", "September 20, 2022", false, "",
                "Java 19 previewed Virtual Threads (Project Loom), Structured Concurrency, and Record Patterns — all transformative features for concurrent and pattern-matching programming.",
                List.of("Virtual Threads (Preview - Project Loom)", "Structured Concurrency (Incubator)", "Record Patterns (Preview)", "Pattern Matching for Switch (Third Preview)", "Foreign Function & Memory API (Preview)", "Vector API (Fourth Incubator)"),
                List.of(new CodeExample("Virtual Threads Preview", "java",
                    "import java.util.concurrent.Executors;\nimport java.time.Duration;\n\npublic class Java19Features {\n    public static void main(String[] args) throws Exception {\n        // Virtual Threads - lightweight threads\n        // Can create millions without exhausting OS threads\n        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {\n            for (int i = 0; i < 10_000; i++) {\n                final int taskId = i;\n                executor.submit(() -> {\n                    // Each runs on a virtual thread\n                    System.out.println(\"Task \" + taskId +\n                        \" on \" + Thread.currentThread());\n                    try {\n                        Thread.sleep(Duration.ofMillis(100));\n                    } catch (InterruptedException e) {\n                        Thread.currentThread().interrupt();\n                    }\n                    return taskId;\n                });\n            }\n        }\n    }\n}",
                    "Virtual Threads are lightweight threads managed by the JVM, not the OS. You can create millions of them, making concurrent programming simple and scalable."))),

            createVersion(21, "Java SE 20", "March 21, 2023", false, "",
                "Java 20 continued refining Virtual Threads, Record Patterns, and Pattern Matching for Switch as preview features. It also continued work on Scoped Values and Structured Concurrency.",
                List.of("Scoped Values (Incubator)", "Record Patterns (Second Preview)", "Pattern Matching for Switch (Fourth Preview)", "Virtual Threads (Second Preview)", "Structured Concurrency (Second Incubator)", "Foreign Function & Memory API (Second Preview)", "Vector API (Fifth Incubator)"),
                List.of(new CodeExample("Record Patterns", "java",
                    "record Point(int x, int y) {}\nrecord Line(Point start, Point end) {}\n\npublic class Java20Features {\n    // Nested Record Patterns\n    static String describeShape(Object obj) {\n        return switch (obj) {\n            case Line(Point(var x1, var y1),\n                      Point(var x2, var y2)) ->\n                \"Line from (\" + x1 + \",\" + y1 +\n                \") to (\" + x2 + \",\" + y2 + \")\";\n            case Point(var x, var y) ->\n                \"Point at (\" + x + \",\" + y + \")\";\n            default -> \"Unknown shape\";\n        };\n    }\n\n    public static void main(String[] args) {\n        var line = new Line(\n            new Point(0, 0), new Point(10, 20));\n        System.out.println(describeShape(line));\n        System.out.println(describeShape(new Point(5, 5)));\n    }\n}",
                    "Record Patterns allow destructuring records in switch and instanceof, enabling deep pattern matching on complex nested data structures."))),

            createVersion(22, "Java SE 21", "September 19, 2023", true, "",
                "Java 21 is a landmark LTS release. It finalized Virtual Threads, Pattern Matching for Switch, Record Patterns, and introduced Sequenced Collections, String Templates (preview), and Unnamed Patterns.",
                List.of("Virtual Threads (Standard - Project Loom)", "Pattern Matching for Switch (Standard)", "Record Patterns (Standard)", "Sequenced Collections", "String Templates (Preview)", "Unnamed Patterns and Variables (Preview)", "Scoped Values (Preview)", "Structured Concurrency (Preview)", "Key Encapsulation Mechanism API", "Generational ZGC"),
                List.of(
                    new CodeExample("Virtual Threads & Sequenced Collections", "java",
                        "import java.util.*;\nimport java.util.concurrent.*;\n\npublic class Java21Features {\n    public static void main(String[] args) throws Exception {\n        // Virtual Threads - now standard!\n        Thread vThread = Thread.ofVirtual()\n            .name(\"my-virtual-thread\")\n            .start(() -> {\n                System.out.println(\"Running on: \" +\n                    Thread.currentThread());\n            });\n        vThread.join();\n\n        // Sequenced Collections - ordered access\n        SequencedCollection<String> list =\n            new ArrayList<>(List.of(\"A\", \"B\", \"C\"));\n        System.out.println(list.getFirst()); // A\n        System.out.println(list.getLast());  // C\n        list.addFirst(\"Z\");\n        System.out.println(list); // [Z, A, B, C]\n\n        // Reversed view\n        SequencedCollection<String> reversed = list.reversed();\n        System.out.println(reversed); // [C, B, A, Z]\n\n        // SequencedMap\n        SequencedMap<String, Integer> map = new LinkedHashMap<>();\n        map.put(\"Java\", 21);\n        map.put(\"Kotlin\", 2);\n        System.out.println(map.firstEntry()); // Java=21\n    }\n}",
                        "Virtual Threads are now production-ready, enabling simple concurrent programming at massive scale. Sequenced Collections add first/last/reversed operations to all ordered collections."),
                    new CodeExample("Pattern Matching for Switch (Final)", "java",
                        "sealed interface Result<T> permits Success, Failure {}\nrecord Success<T>(T value) implements Result<T> {}\nrecord Failure<T>(String error) implements Result<T> {}\n\npublic class Java21Patterns {\n    static <T> void handle(Result<T> result) {\n        switch (result) {\n            case Success<T>(var value) ->\n                System.out.println(\"Success: \" + value);\n            case Failure<T>(var error) ->\n                System.out.println(\"Error: \" + error);\n        }\n    }\n\n    // Guarded patterns\n    static String classify(Object obj) {\n        return switch (obj) {\n            case Integer i when i > 0 -> \"Positive int\";\n            case Integer i when i < 0 -> \"Negative int\";\n            case Integer i -> \"Zero\";\n            case String s when s.isEmpty() -> \"Empty string\";\n            case String s -> \"String: \" + s;\n            case null -> \"null\";\n            default -> obj.toString();\n        };\n    }\n\n    public static void main(String[] args) {\n        handle(new Success<>(\"Data loaded\"));\n        handle(new Failure<>(\"Connection timeout\"));\n        System.out.println(classify(42));\n        System.out.println(classify(-1));\n        System.out.println(classify(\"\"));\n    }\n}",
                        "Pattern matching with guards (when clauses) enables powerful, readable conditional logic that replaces complex if-else chains."))),

            createVersion(23, "Java SE 22", "March 19, 2024", false, "",
                "Java 22 introduced Statements before super(), Unnamed Variables, launched the Stream Gatherers API (preview), and continued evolving the Foreign Function & Memory API.",
                List.of("Statements before super() (Preview)", "Unnamed Variables & Patterns (Standard)", "Stream Gatherers (Preview)", "String Templates (Second Preview)", "Foreign Function & Memory API (Standard)", "Implicitly Declared Classes (Second Preview)", "Class-File API (Preview)", "Structured Concurrency (Second Preview)", "Scoped Values (Second Preview)", "Vector API (Seventh Incubator)"),
                List.of(new CodeExample("Unnamed Variables & Stream Gatherers", "java",
                    "import java.util.List;\nimport java.util.stream.Gatherers;\n\npublic class Java22Features {\n    public static void main(String[] args) {\n        // Unnamed variables with _\n        var items = List.of(\"a\", \"b\", \"c\");\n        for (var _ : items) {\n            // We don't need the variable\n            System.out.println(\"Processing...\");\n        }\n\n        try {\n            int result = Integer.parseInt(\"abc\");\n        } catch (NumberFormatException _) {\n            // Don't need the exception variable\n            System.out.println(\"Invalid number\");\n        }\n\n        // Stream Gatherers (preview) - custom intermediate ops\n        var result = List.of(1, 2, 3, 4, 5, 6, 7, 8)\n            .stream()\n            .gather(Gatherers.windowFixed(3))\n            .toList();\n        System.out.println(result);\n        // [[1,2,3], [4,5,6], [7,8]]\n    }\n}",
                    "Unnamed variables (_) indicate intentionally unused variables, improving readability. Stream Gatherers allow custom intermediate stream operations."))),

            createVersion(24, "Java SE 23", "September 17, 2024", false, "",
                "Java 23 continued the evolution with Markdown Documentation Comments, Primitive Types in Patterns (preview), Module Import Declarations (preview), and Flexible Constructor Bodies.",
                List.of("Markdown Documentation Comments", "Primitive Types in Patterns (Preview)", "Module Import Declarations (Preview)", "Flexible Constructor Bodies (Second Preview)", "Stream Gatherers (Second Preview)", "Structured Concurrency (Third Preview)", "Scoped Values (Third Preview)", "Class-File API (Second Preview)", "ZGC: Generational Mode by Default", "Vector API (Eighth Incubator)"),
                List.of(new CodeExample("Markdown Doc Comments & Primitives in Patterns", "java",
                    "/// This is a **Markdown** documentation comment.\n/// \n/// ## Features\n/// - Supports _italic_ and **bold**\n/// - Code blocks with ```java\n/// - Links like [Java Hub](https://javahub.dev)\n///\n/// @param radius the circle radius\n/// @return the computed area\npublic class Java23Features {\n\n    /// Computes the area of a circle.\n    static double area(double radius) {\n        return Math.PI * radius * radius;\n    }\n\n    // Primitive types in patterns (preview)\n    static String classify(Object obj) {\n        return switch (obj) {\n            case Integer i when i > 100 -> \"Large\";\n            case Integer i -> \"Small: \" + i;\n            default -> \"Other\";\n        };\n    }\n\n    public static void main(String[] args) {\n        System.out.println(area(5.0));\n        System.out.println(classify(150));\n    }\n}",
                    "Markdown doc comments (///) replace the old HTML-based Javadoc style with modern Markdown, making documentation much more readable in source."))),

            createVersion(25, "Java SE 24", "March 18, 2025", false, "",
                "Java 24 brought Stream Gatherers to standard, previewed Simple Source Files, and continued refining Structured Concurrency. It also introduced Compact Source Files & Instance Main Methods.",
                List.of("Stream Gatherers (Standard)", "Simple Source Files and Instance Main Methods (Preview)", "Flexible Constructor Bodies (Standard)", "Module Import Declarations (Second Preview)", "Primitive Types in Patterns (Second Preview)", "Structured Concurrency (Fourth Preview)", "Scoped Values (Fourth Preview)", "Key Derivation Function API (Preview)", "Ahead-of-Time Class Loading & Linking"),
                List.of(new CodeExample("Stream Gatherers (Standard) & Simple Source Files", "java",
                    "import java.util.List;\nimport java.util.stream.Gatherers;\n\n// Simple source file - no explicit class needed (preview)\nvoid main() {\n    var data = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);\n\n    // Stream Gatherers - now standard!\n    // Sliding window\n    var windows = data.stream()\n        .gather(Gatherers.windowSliding(3))\n        .toList();\n    System.out.println(\"Sliding windows: \" + windows);\n\n    // Fixed window\n    var fixed = data.stream()\n        .gather(Gatherers.windowFixed(4))\n        .toList();\n    System.out.println(\"Fixed windows: \" + fixed);\n\n    // Fold (reduce with seed)\n    var sum = data.stream()\n        .gather(Gatherers.fold(\n            () -> 0,\n            (acc, elem) -> acc + elem))\n        .findFirst()\n        .orElse(0);\n    System.out.println(\"Sum: \" + sum);\n}",
                    "Stream Gatherers are now a standard feature, providing powerful custom intermediate stream operations like windowing and folding."))),

            createVersion(26, "Java SE 25", "September 16, 2025", true, "",
                "Java 25 is the latest Long-Term Support release, bringing stability and enterprise readiness. It finalized several features including Structured Concurrency and Scoped Values.",
                List.of("Structured Concurrency (Standard)", "Scoped Values (Standard)", "Compact Source Files (Second Preview)", "Primitive Types in Patterns (Standard)", "Stable Foreign Function & Memory API enhancements", "Performance improvements across GCs", "Security enhancements"),
                List.of(new CodeExample("Structured Concurrency & Scoped Values", "java",
                    "import java.util.concurrent.StructuredTaskScope;\nimport java.lang.ScopedValue;\n\npublic class Java25Features {\n    // ScopedValue - thread-safe context propagation\n    static final ScopedValue<String> USER = ScopedValue.newInstance();\n\n    static void handleRequest() throws Exception {\n        // Structured Concurrency - manage concurrent subtasks\n        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {\n            var userTask = scope.fork(() -> fetchUser());\n            var orderTask = scope.fork(() -> fetchOrders());\n\n            scope.join();           // Wait for all\n            scope.throwIfFailed();  // Propagate errors\n\n            System.out.println(\"User: \" + userTask.get());\n            System.out.println(\"Orders: \" + orderTask.get());\n        }\n    }\n\n    static String fetchUser() {\n        return \"User: \" + USER.get();\n    }\n\n    static String fetchOrders() {\n        return \"Orders for \" + USER.get();\n    }\n\n    public static void main(String[] args) throws Exception {\n        ScopedValue.runWhere(USER, \"Alice\",\n            () -> {\n                try { handleRequest(); }\n                catch (Exception e) { e.printStackTrace(); }\n            });\n    }\n}",
                    "Structured Concurrency treats groups of related tasks as a unit, ensuring proper cleanup and error propagation. ScopedValues replace ThreadLocal with a safer, more efficient alternative."))),

            createVersion(27, "Java SE 26", "March 18, 2026", false, "",
                "Java 26 is the latest feature release, introducing HPKE encryption support, post-quantum ready JAR signing, HTTP/3 support, and continued evolution of the platform.",
                List.of("HPKE (Hybrid Public Key Encryption)", "Post-Quantum Ready JAR Signing", "HTTP/3 Support", "Compact Source Files (Standard)", "Enhanced Pattern Matching", "Continued GC improvements", "Security and performance enhancements"),
                List.of(new CodeExample("Java 26 - Latest Features", "java",
                    "// Compact Source File - no class declaration needed!\nimport java.net.http.*;\nimport java.net.URI;\n\n// Instance main method - simplest Java program ever\nvoid main() {\n    System.out.println(\"Welcome to Java 26!\");\n    System.out.println(\"Latest feature release: March 2026\");\n\n    // HTTP/3 support\n    var client = HttpClient.newBuilder()\n        .version(HttpClient.Version.HTTP_2)\n        .build();\n\n    System.out.println(\"Key highlights:\");\n    System.out.println(\"- HPKE encryption support\");\n    System.out.println(\"- Post-quantum ready JAR signing\");\n    System.out.println(\"- HTTP/3 support\");\n    System.out.println(\"- Compact source files (standard)\");\n}",
                    "Java 26 represents the cutting edge of the platform with post-quantum cryptography support and the simplest-ever program structure with compact source files."))
            )
        );
        versionRepo.saveAll(versions);
    }

    private JavaVersion createVersion(int num, String name, String date, boolean lts,
                                       String codename, String desc,
                                       List<String> features, List<CodeExample> examples) {
        JavaVersion v = new JavaVersion();
        v.setVersionNumber(num);
        v.setReleaseName(name);
        v.setReleaseDate(date);
        v.setLts(lts);
        v.setCodename(codename);
        v.setDescription(desc);
        v.setKeyFeatures(features);
        v.setCodeExamples(examples);
        return v;
    }

    private void seedTopics() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:data/topics/*.json");
            ObjectMapper mapper = new ObjectMapper();
            List<Topic> topics = new java.util.ArrayList<>();
            for (Resource res : resources) {
                Topic t = mapper.readValue(res.getInputStream(), Topic.class);
                topics.add(t);
            }
            topicRepo.saveAll(topics);
            log.info("Seeded {} topics from JSON", topics.size());
        } catch (Exception e) {
            log.error("Failed to seed topics", e);
        }
    }

    private void seedFrameworks() {
        List<Framework> frameworks = new java.util.ArrayList<>(List.of(
            createFramework("Spring Boot", "spring-boot", "Web",
                "The most popular Java framework for building production-ready, stand-alone Spring applications with minimal configuration.",
                "## Spring Boot Overview\n\nSpring Boot makes it easy to create stand-alone, production-grade Spring-based applications. It takes an opinionated view of the Spring platform to minimize boilerplate configuration.\n\n### Core Concepts\n- **Auto-Configuration** - Automatically configures beans based on classpath, property settings, and other beans. It aims to configure your application exactly how you need it without manual XML or Java configuration.\n- **Starter Dependencies** - Curated dependency descriptors (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`) that you can include in your application. They give you a one-stop-shop for all the Spring and related technology that you need.\n- **Embedded Server** - Runs Tomcat, Jetty, or Undertow embedded so you don't have to deploy war files.\n- **Actuator** - Production-ready features to help you monitor and manage your application (health checks, metrics, HTTP tracing).\n- **Spring Boot DevTools** - Modules to make the development experience a little more pleasant (automatic restarts, live reload).",
                List.of("Auto-configuration", "Embedded web server", "Starter POMs", "Actuator for monitoring", "Spring Boot DevTools", "Externalized configuration", "Production-ready features"),
                "### Getting Started\n\n1. Go to [start.spring.io](https://start.spring.io)\n2. Select dependencies: Spring Web, Spring Data JPA\n3. Download and unzip\n4. Run: `mvn spring-boot:run`",
                List.of(
                    new CodeExample("REST API with Spring Boot", "java",
                        "@RestController\n@RequestMapping(\"/api/users\")\npublic class UserController {\n\n    @Autowired\n    private UserService userService;\n\n    @GetMapping\n    public List<User> getAllUsers() {\n        return userService.findAll();\n    }\n\n    @GetMapping(\"/{id}\")\n    public ResponseEntity<User> getUser(@PathVariable Long id) {\n        return userService.findById(id)\n            .map(ResponseEntity::ok)\n            .orElse(ResponseEntity.notFound().build());\n    }\n\n    @PostMapping\n    @ResponseStatus(HttpStatus.CREATED)\n    public User createUser(@Valid @RequestBody User user) {\n        return userService.save(user);\n    }\n\n    @PutMapping(\"/{id}\")\n    public User updateUser(@PathVariable Long id,\n                           @Valid @RequestBody User user) {\n        return userService.update(id, user);\n    }\n\n    @DeleteMapping(\"/{id}\")\n    @ResponseStatus(HttpStatus.NO_CONTENT)\n    public void deleteUser(@PathVariable Long id) {\n        userService.delete(id);\n    }\n}",
                        "A complete CRUD REST controller with proper HTTP status codes and request validation."),
                    new CodeExample("Spring Boot Configuration", "java",
                        "// Application Properties\n// application.yml\n// server:\n//   port: 8080\n// spring:\n//   datasource:\n//     url: jdbc:postgresql://localhost:5432/mydb\n//     username: admin\n//   jpa:\n//     hibernate:\n//       ddl-auto: update\n\n@Configuration\npublic class AppConfig {\n\n    @Bean\n    public RestTemplate restTemplate() {\n        return new RestTemplate();\n    }\n\n    @Bean\n    public WebMvcConfigurer corsConfigurer() {\n        return new WebMvcConfigurer() {\n            @Override\n            public void addCorsMappings(CorsRegistry registry) {\n                registry.addMapping(\"/api/**\")\n                    .allowedOrigins(\"http://localhost:3000\");\n            }\n        };\n    }\n}",
                        "Spring Boot uses convention over configuration, but you can customize everything through properties or @Configuration classes.")),
                List.of("Microservices", "REST APIs", "Web Applications", "Cloud Native Apps", "Enterprise Applications"),
                "https://spring.io/projects/spring-boot"),

            createFramework("Hibernate / JPA", "hibernate-jpa", "ORM",
                "The standard ORM (Object-Relational Mapping) framework for Java, implementing the JPA specification for database persistence.",
                "## Hibernate & JPA\n\nHibernate is the most popular JPA implementation, providing object-relational mapping to store Java objects in relational databases.\n\n### Key Concepts\n- **Entity** - A lightweight persistence domain object. Typically an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.\n- **EntityManager** - The API for interacting with the persistence context. Used to create and remove persistent entity instances, to find entities by their primary key, and to query over entities.\n- **JPQL** - Java Persistence Query Language. An object-oriented query language used to perform database operations on persistent entities.\n- **Criteria API** - A predefined API used to define queries for entities. It is an alternative to JPQL and is particularly useful for building dynamic queries programmatically.\n- **Caching** - \n  - *First-level cache*: Associated with the Session object (EntityManager). It is enabled by default and you cannot disable it.\n  - *Second-level cache*: Associated with the SessionFactory object. It is disabled by default.",
                List.of("Object-Relational Mapping", "JPA specification implementation", "HQL / JPQL queries", "Criteria API", "First and Second-level caching", "Lazy/Eager loading", "Transaction management", "Database schema generation"),
                "### Getting Started\n\nAdd `spring-boot-starter-data-jpa` and your database driver to your Spring Boot project.",
                List.of(new CodeExample("JPA Entity & Repository", "java",
                    "@Entity\n@Table(name = \"products\")\npublic class Product {\n    @Id\n    @GeneratedValue(strategy = GenerationType.IDENTITY)\n    private Long id;\n\n    @Column(nullable = false)\n    private String name;\n\n    private double price;\n\n    @ManyToOne(fetch = FetchType.LAZY)\n    @JoinColumn(name = \"category_id\")\n    private Category category;\n\n    @OneToMany(mappedBy = \"product\", cascade = CascadeType.ALL)\n    private List<Review> reviews = new ArrayList<>();\n}\n\n// Spring Data JPA Repository\npublic interface ProductRepository extends JpaRepository<Product, Long> {\n    List<Product> findByNameContaining(String name);\n    List<Product> findByPriceBetween(double min, double max);\n\n    @Query(\"SELECT p FROM Product p WHERE p.category.name = :cat\")\n    List<Product> findByCategoryName(@Param(\"cat\") String category);\n}",
                    "JPA entities map directly to database tables. Spring Data JPA repositories provide CRUD operations and query derivation from method names.")),
                List.of("Database persistence", "Enterprise applications", "Complex data models", "Transaction management"),
                "https://hibernate.org"),

            createFramework("Spring Security", "spring-security", "Security",
                "Comprehensive security framework for Java applications providing authentication, authorization, and protection against common attacks.",
                "## Spring Security\n\nSpring Security is the de facto standard for securing Spring-based applications, providing both authentication and authorization.\n\n### Key Concepts\n- **Authentication** - Verifying who the user is (e.g., username/password, JWT, OAuth2).\n- **Authorization** - Determining what the authenticated user is allowed to do (e.g., Roles, Authorities).\n- **SecurityFilterChain** - A chain of filters that Spring Security uses to intercept requests and apply security logic (e.g., `UsernamePasswordAuthenticationFilter`, `CsrfFilter`).\n- **Protection** - Built-in protection against Session Fixation, Clickjacking, CSRF (Cross-Site Request Forgery), and more.\n- **Method Security** - Fine-grained security at the method level using annotations like `@PreAuthorize` and `@PostAuthorize`.",
                List.of("Authentication (Form, JWT, OAuth2)", "Authorization (Role-based, Method-level)", "CSRF protection", "Session management", "Password encoding", "OAuth2 / OpenID Connect", "CORS configuration"),
                "### Getting Started\n\nAdd `spring-boot-starter-security` to your project.",
                List.of(new CodeExample("Security Configuration", "java",
                    "@Configuration\n@EnableWebSecurity\n@EnableMethodSecurity\npublic class SecurityConfig {\n\n    @Bean\n    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {\n        return http\n            .csrf(csrf -> csrf.disable()) // Often disabled for stateless APIs\n            .authorizeHttpRequests(auth -> auth\n                .requestMatchers(\"/api/public/**\").permitAll()\n                .requestMatchers(\"/api/admin/**\").hasRole(\"ADMIN\")\n                .anyRequest().authenticated())\n            .sessionManagement(session -> session\n                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))\n            .httpBasic(Customizer.withDefaults())\n            .build();\n    }\n\n    @Bean\n    public PasswordEncoder passwordEncoder() {\n        return new BCryptPasswordEncoder();\n    }\n\n    @Bean\n    public UserDetailsService userDetailsService() {\n        var user = User.withUsername(\"user\")\n            .password(passwordEncoder().encode(\"password\"))\n            .roles(\"USER\")\n            .build();\n        return new InMemoryUserDetailsManager(user);\n    }\n}",
                    "Spring Security 6 uses a lambda-based DSL for configuring security. The filter chain defines authentication and authorization rules.")),
                List.of("API security", "Web application security", "OAuth2 integration", "Enterprise security"),
                "https://spring.io/projects/spring-security"),

            createFramework("JUnit & Mockito", "junit-mockito", "Testing",
                "The standard testing frameworks for Java: JUnit 5 for unit testing and Mockito for mocking dependencies.",
                "## Testing with JUnit 5 & Mockito\n\nJUnit 5 (Jupiter) is the next generation testing framework for Java. Mockito is the most popular mocking framework.\n\n### JUnit 5 Features\n- `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`\n- `@ParameterizedTest` and `@ValueSource` for data-driven testing\n- `@Nested` for grouping related tests\n- Assertions (`assertEquals`, `assertThrows`) and Assumptions (`assumeTrue`)\n- Extensions (`@ExtendWith`)\n\n### Mockito Features\n- **Mocking**: Creating dummy objects for dependencies (`@Mock`, `Mockito.mock()`).\n- **Stubbing**: Telling the mock what to return (`when(mock.method()).thenReturn(value)`).\n- **Verification**: Checking if a method was called (`verify(mock).method()`).\n- **Argument Captors**: Capturing arguments passed to mocked methods for assertions.",
                List.of("JUnit 5 annotations", "Parameterized tests", "Nested test classes", "Mockito mocking", "Argument captors", "BDD style (given/when/then)", "Spring Boot test integration"),
                "### Getting Started\n\nSpring Boot includes `spring-boot-starter-test` which bundles JUnit 5, Mockito, and AssertJ.",
                List.of(new CodeExample("JUnit 5 & Mockito Testing", "java",
                    "import org.junit.jupiter.api.*;\nimport org.mockito.*;\nimport static org.junit.jupiter.api.Assertions.*;\nimport static org.mockito.Mockito.*;\n\nclass UserServiceTest {\n\n    @Mock\n    UserRepository userRepo;\n\n    @InjectMocks\n    UserService userService;\n\n    @BeforeEach\n    void setUp() {\n        MockitoAnnotations.openMocks(this);\n    }\n\n    @Test\n    @DisplayName(\"Should find user by ID\")\n    void testFindById() {\n        // Given\n        var user = new User(1L, \"Alice\", \"alice@email.com\");\n        when(userRepo.findById(1L)).thenReturn(Optional.of(user));\n\n        // When\n        var result = userService.findById(1L);\n\n        // Then\n        assertTrue(result.isPresent());\n        assertEquals(\"Alice\", result.get().getName());\n        verify(userRepo, times(1)).findById(1L);\n    }\n\n    @ParameterizedTest\n    @ValueSource(strings = {\"alice@test.com\", \"bob@test.com\"})\n    void testValidEmail(String email) {\n        assertTrue(userService.isValidEmail(email));\n    }\n\n    @Nested\n    @DisplayName(\"When user does not exist\")\n    class WhenUserNotFound {\n        @Test\n        void shouldReturnEmpty() {\n            when(userRepo.findById(99L)).thenReturn(Optional.empty());\n            assertTrue(userService.findById(99L).isEmpty());\n        }\n    }\n}",
                    "JUnit 5 with Mockito follows the Given-When-Then pattern. @Nested classes help organize related tests logically.")),
                List.of("Unit testing", "Integration testing", "TDD (Test-Driven Development)", "Regression testing"),
                "https://junit.org/junit5/"),

            createFramework("Apache Kafka", "apache-kafka", "Messaging",
                "Distributed event streaming platform for building real-time data pipelines and streaming applications.",
                "## Apache Kafka with Java\n\nKafka is a distributed event streaming platform capable of handling trillions of events per day.\n\n### Key Concepts\n- **Producer** - An application that publishes (writes) events to a Kafka cluster.\n- **Consumer** - An application that subscribes to (reads) events from a Kafka cluster.\n- **Topic** - A category or feed name to which events are published. Topics are partitioned for scalability.\n- **Partition** - An ordered, immutable sequence of records that is continually appended to. Partitions allow topics to parallelize reading and writing.\n- **Consumer Group** - A set of consumers that cooperate to consume data from some topics. Each partition is consumed by exactly one consumer in the group.",
                List.of("High throughput", "Fault tolerant", "Horizontal scalability", "Real-time streaming", "Event sourcing", "Message replay", "Spring Kafka integration"),
                "### Getting Started\n\nAdd `spring-kafka` dependency to your Spring Boot project.",
                List.of(new CodeExample("Kafka Producer & Consumer", "java",
                    "// Producer\n@Service\npublic class OrderEventProducer {\n    @Autowired\n    private KafkaTemplate<String, String> kafkaTemplate;\n\n    public void sendOrderEvent(String orderId, String event) {\n        kafkaTemplate.send(\"order-events\", orderId, event);\n    }\n}\n\n// Consumer\n@Service\npublic class OrderEventConsumer {\n    @KafkaListener(topics = \"order-events\", groupId = \"order-service\")\n    public void handleOrderEvent(String message) {\n        System.out.println(\"Received: \" + message);\n        // Process the order event\n    }\n}\n\n// Configuration\n@Configuration\npublic class KafkaConfig {\n    @Bean\n    public NewTopic orderTopic() {\n        return TopicBuilder.name(\"order-events\")\n            .partitions(3)\n            .replicas(1)\n            .build();\n    }\n}",
                    "Spring Kafka simplifies Kafka integration with annotation-driven consumers and KafkaTemplate for producers.")),
                List.of("Event-driven architecture", "Microservices communication", "Log aggregation", "Real-time analytics"),
                "https://kafka.apache.org"),

            createFramework("Maven & Gradle", "maven-gradle", "Build",
                "The two dominant build tools for Java projects: Maven for convention-based builds and Gradle for flexible, high-performance builds.",
                "## Build Tools: Maven & Gradle\n\nMaven and Gradle are essential build tools that manage dependencies, compile code, run tests, and package applications.\n\n### Maven\n- Uses XML for configuration (`pom.xml`).\n- Strict convention-over-configuration lifecycle (validate, compile, test, package, verify, install, deploy).\n- Easier to learn and widely adopted in older projects.\n\n### Gradle\n- Uses Groovy or Kotlin DSL for configuration (`build.gradle` or `build.gradle.kts`).\n- Highly flexible and faster due to incremental builds and build cache.\n- Preferred for Android development and modern large-scale Java projects.",
                List.of("Dependency management", "Build lifecycle", "Plugin ecosystem", "Multi-module projects", "CI/CD integration", "Reproducible builds"),
                "### Maven\n```xml\n<dependency>\n    <groupId>org.springframework.boot</groupId>\n    <artifactId>spring-boot-starter-web</artifactId>\n</dependency>\n```\n\n### Gradle\n```groovy\nimplementation 'org.springframework.boot:spring-boot-starter-web'\n```",
                List.of(new CodeExample("Maven pom.xml & Gradle build.gradle", "xml",
                    "<!-- Maven pom.xml -->\n<project>\n    <modelVersion>4.0.0</modelVersion>\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>3.4.1</version>\n    </parent>\n    <groupId>com.example</groupId>\n    <artifactId>my-app</artifactId>\n    <version>1.0.0</version>\n\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-test</artifactId>\n            <scope>test</scope>\n        </dependency>\n    </dependencies>\n\n    <build>\n        <plugins>\n            <plugin>\n                <groupId>org.springframework.boot</groupId>\n                <artifactId>spring-boot-maven-plugin</artifactId>\n            </plugin>\n        </plugins>\n    </build>\n</project>",
                    "Maven uses XML-based configuration with a convention-over-configuration approach. Gradle uses Groovy/Kotlin DSL for more flexible builds.")),
                List.of("Project building", "Dependency management", "CI/CD pipelines", "Multi-module projects"),
                "https://maven.apache.org"),

            createFramework("Microservices Architecture", "microservices", "Architecture",
                "Build scalable, distributed systems using Spring Cloud and microservices patterns with Java.",
                "## Microservices with Java\n\nMicroservices architecture decomposes a monolithic application into small, independently deployable services organized around business capabilities.\n\n### Spring Cloud Components\n- **Service Discovery** (Eureka, Consul): Allows services to find each other without hardcoding IP addresses.\n- **API Gateway** (Spring Cloud Gateway): A single entry point for routing, rate limiting, and cross-cutting concerns.\n- **Centralized Configuration** (Spring Cloud Config): Externalize configuration in a Git repository.\n- **Circuit Breaker** (Resilience4j): Prevents cascading failures when a downstream service is down.\n- **Declarative REST Clients** (OpenFeign): Simplifies HTTP communication between services.\n- **Distributed Tracing** (Micrometer Tracing, Zipkin): Track requests across multiple microservices.",
                List.of("Service Discovery (Eureka)", "API Gateway", "Circuit Breaker (Resilience4j)", "Distributed Tracing", "Config Server", "Load Balancing", "Event-Driven Communication"),
                "### Getting Started\n\nUse Spring Cloud starters with Spring Boot.",
                List.of(new CodeExample("Microservice Components", "java",
                    "// Service Discovery Client\n@SpringBootApplication\n@EnableDiscoveryClient\npublic class OrderServiceApp {\n    public static void main(String[] args) {\n        SpringApplication.run(OrderServiceApp.class, args);\n    }\n}\n\n// Feign Client - declarative REST client\n@FeignClient(name = \"user-service\")\npublic interface UserClient {\n    @GetMapping(\"/api/users/{id}\")\n    User getUserById(@PathVariable Long id);\n}\n\n// Circuit Breaker with Resilience4j\n@Service\npublic class OrderService {\n    @CircuitBreaker(name = \"userService\", fallbackMethod = \"fallback\")\n    public User getUser(Long id) {\n        return userClient.getUserById(id);\n    }\n\n    public User fallback(Long id, Exception e) {\n        return new User(id, \"Unknown\", \"N/A\");\n    }\n}\n\n// API Gateway Route\n@Configuration\npublic class GatewayConfig {\n    @Bean\n    public RouteLocator routes(RouteLocatorBuilder builder) {\n        return builder.routes()\n            .route(\"users\", r -> r.path(\"/api/users/**\")\n                .uri(\"lb://user-service\"))\n            .route(\"orders\", r -> r.path(\"/api/orders/**\")\n                .uri(\"lb://order-service\"))\n            .build();\n    }\n}",
                    "Spring Cloud provides all the building blocks for microservices: service discovery, API gateway, circuit breakers, and declarative clients.")),
                List.of("Distributed systems", "Cloud-native applications", "Scalable architectures", "Enterprise applications"),
                "https://spring.io/projects/spring-cloud"),

            createFramework("Log4j & SLF4J", "logging", "Logging",
                "Java logging frameworks: SLF4J as the logging facade and Log4j2/Logback as implementations for structured, configurable logging.",
                "## Java Logging\n\nLogging is critical for observing application behavior in production. Java uses a facade pattern for logging.\n\n### SLF4J (Simple Logging Facade for Java)\nSLF4J acts as a facade or abstraction for various logging frameworks (e.g., java.util.logging, logback, log4j2) allowing the end user to plug in the desired logging framework at deployment time.\n\n### Implementations\n- **Logback**: The default logging implementation in Spring Boot. Very fast and natively implements SLF4J.\n- **Log4j2**: Known for high performance (asynchronous loggers via LMAX Disruptor) and advanced filtering.\n\n### Best Practices\n- **MDC (Mapped Diagnostic Context)**: Thread-local map used to inject contextual data (like user ID or request ID) into every log statement. Essential for microservices tracing.\n- **Log Levels**: Use appropriate levels: TRACE, DEBUG, INFO, WARN, ERROR.",
                List.of("SLF4J facade pattern", "Log4j2 high-performance logging", "Logback integration", "Log levels (TRACE, DEBUG, INFO, WARN, ERROR)", "Structured logging", "Log rotation", "MDC (Mapped Diagnostic Context)"),
                "### Getting Started\n\nSpring Boot uses Logback by default. Add SLF4J annotations with Lombok's `@Slf4j`.",
                List.of(new CodeExample("Logging Best Practices", "java",
                    "import org.slf4j.Logger;\nimport org.slf4j.LoggerFactory;\nimport org.slf4j.MDC;\nimport lombok.extern.slf4j.Slf4j;\n\n@Slf4j // Lombok annotation generates the 'log' variable\npublic class LoggingDemo {\n\n    public void processOrder(String orderId, String userId) {\n        // MDC for contextual logging (adds to all logs in this thread)\n        MDC.put(\"orderId\", orderId);\n        MDC.put(\"userId\", userId);\n\n        log.info(\"Processing order\"); // Output will include orderId and userId\n\n        try {\n            log.debug(\"Validating order details\");\n            // validateOrder(orderId);\n            log.info(\"Order processed successfully\");\n        } catch (Exception e) {\n            log.error(\"Failed to process order\", e);\n        } finally {\n            // Always clear MDC to prevent memory leaks in thread pools\n            MDC.clear();\n        }\n    }\n}",
                    "Use SLF4J with parameterized messages ({}), MDC for request tracing, and appropriate log levels for each situation.")),
                List.of("Application monitoring", "Debugging", "Audit trails", "Production troubleshooting"),
                "https://www.slf4j.org")
        ));

        // Add new frameworks
        frameworks.add(createFramework("Spring MVC", "spring-mvc", "Web",
            "The foundational web framework in Spring, providing a Model-View-Controller architecture for building web applications and REST APIs.",
            "## Spring MVC\n\nSpring Web MVC is the original web framework built on the Servlet API. It's the engine behind most Spring Boot web applications.\n\n### Key Concepts\n- **DispatcherServlet**: The central front controller that routes incoming HTTP requests to handlers (controllers).\n- **Controllers**: Classes annotated with `@Controller` or `@RestController` that handle requests.\n- **Mapping**: `@GetMapping`, `@PostMapping`, etc., bind HTTP methods and URLs to controller methods.\n- **Exception Handling**: Global exception handling via `@ControllerAdvice` and `@ExceptionHandler`.\n- **Data Binding**: Automatically binds HTTP request parameters to Java objects.",
            List.of("DispatcherServlet", "ControllerAdvice", "REST APIs", "Content Negotiation", "Interceptors", "View Resolution"),
            "### Getting Started\n\nIncluded automatically with `spring-boot-starter-web`.",
            List.of(new CodeExample("Global Exception Handling in Spring MVC", "java",
                "@RestControllerAdvice\npublic class GlobalExceptionHandler {\n\n    @ExceptionHandler(UserNotFoundException.class)\n    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {\n        ErrorResponse error = new ErrorResponse(\"USER_NOT_FOUND\", ex.getMessage());\n        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);\n    }\n\n    @ExceptionHandler(MethodArgumentNotValidException.class)\n    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {\n        Map<String, String> errors = new HashMap<>();\n        ex.getBindingResult().getAllErrors().forEach((error) -> {\n            String fieldName = ((FieldError) error).getField();\n            String errorMessage = error.getDefaultMessage();\n            errors.put(fieldName, errorMessage);\n        });\n        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);\n    }\n}",
                "@RestControllerAdvice provides centralized exception handling across all @RequestMapping methods.")),
            List.of("RESTful Web Services", "Server-Side Rendering", "Microservices APIs"),
            "https://docs.spring.io/spring-framework/reference/web/webmvc.html"));

        frameworks.add(createFramework("Spring Data JPA", "spring-data-jpa", "ORM",
            "Simplifies data access layer implementation by reducing boilerplate code required to interact with JPA.",
            "## Spring Data JPA\n\nSpring Data JPA is a powerful abstraction on top of JPA that significantly reduces the amount of boilerplate code needed to implement data access layers.\n\n### Key Concepts\n- **Repository Interfaces**: Simply declare an interface extending `JpaRepository` or `CrudRepository`, and Spring generates the implementation at runtime.\n- **Query Derivation**: Spring can parse method names like `findByLastNameAndFirstName` and generate the corresponding JPQL query automatically.\n- **@Query Annotation**: For complex queries, you can manually define JPQL or native SQL queries using `@Query`.\n- **Pagination & Sorting**: Built-in support for paginated queries by passing `Pageable` objects.\n- **Projections**: Fetching only a subset of columns from the database instead of the whole entity.",
            List.of("Query derivation", "Pagination and Sorting", "Auditing", "Projections", "Specifications"),
            "### Getting Started\n\nAdd `spring-boot-starter-data-jpa` dependency.",
            List.of(new CodeExample("Advanced Spring Data JPA", "java",
                "// DTO Projection\npublic interface UserSummary {\n    String getUsername();\n    String getEmail();\n}\n\npublic interface UserRepository extends JpaRepository<User, Long> {\n    // Query Derivation\n    List<User> findByStatusAndAgeGreaterThan(String status, int age);\n\n    // Pagination & Sorting\n    Page<User> findByDepartment(String dept, Pageable pageable);\n\n    // Projections (returns DTOs, faster query)\n    List<UserSummary> findByRole(String role);\n\n    // Custom JPQL\n    @Query(\"SELECT u FROM User u WHERE u.email LIKE %:domain%\")\n    List<User> findUsersByEmailDomain(@Param(\"domain\") String domain);\n\n    // Modifying Queries\n    @Modifying\n    @Query(\"UPDATE User u SET u.status = 'INACTIVE' WHERE u.lastLogin < :date\")\n    int deactivateInactiveUsers(@Param(\"date\") LocalDateTime date);\n}",
                "Spring Data JPA repositories make CRUD and complex queries incredibly concise.")),
            List.of("Database Integration", "Rapid Development", "Microservices Data Access"),
            "https://spring.io/projects/spring-data-jpa"));

        frameworks.add(createFramework("Spring AOP", "spring-aop", "Core",
            "Aspect-Oriented Programming framework to separate cross-cutting concerns (like logging, security, transactions) from business logic.",
            "## Spring AOP\n\nAspect-Oriented Programming (AOP) entails breaking down program logic into distinct parts called concerns. It is used to modularize cross-cutting concerns.\n\n### Key Concepts\n- **Aspect**: A modularization of a concern that cuts across multiple classes (e.g., transaction management).\n- **Join Point**: A point during the execution of a program, such as the execution of a method. Spring AOP only supports method execution join points.\n- **Advice**: Action taken by an aspect at a particular join point (`@Before`, `@After`, `@Around`).\n- **Pointcut**: A predicate that matches join points (e.g., \"all methods in the service package\").\n- **Weaving**: Linking aspects with other application types or objects to create an advised object. Spring AOP performs weaving at runtime using JDK dynamic proxies or CGLIB.",
            List.of("Aspects", "Join Points", "Pointcuts", "Advice (@Around, @Before)", "Cross-cutting concerns"),
            "### Getting Started\n\nAdd `spring-boot-starter-aop` to use AOP in Spring Boot.",
            List.of(new CodeExample("Logging Aspect with @Around", "java",
                "import org.aspectj.lang.ProceedingJoinPoint;\nimport org.aspectj.lang.annotation.*;\nimport org.springframework.stereotype.Component;\nimport lombok.extern.slf4j.Slf4j;\n\n@Aspect\n@Component\n@Slf4j\npublic class LoggingAspect {\n\n    // Pointcut targeting all methods in the service package\n    @Pointcut(\"execution(* com.example.service.*.*(..))\")\n    public void serviceMethods() {}\n\n    @Around(\"serviceMethods()\")\n    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {\n        long start = System.currentTimeMillis();\n\n        String methodName = joinPoint.getSignature().getName();\n        log.info(\"Executing method: {}\", methodName);\n\n        // Proceed with the actual method execution\n        Object proceed = joinPoint.proceed();\n\n        long executionTime = System.currentTimeMillis() - start;\n        log.info(\"Method {} executed in {} ms\", methodName, executionTime);\n\n        return proceed;\n    }\n}",
                "The @Around advice wraps the method, allowing you to execute code before and after the method, measure execution time, or even prevent execution.")),
            List.of("Transaction Management", "Logging", "Security Checks", "Performance Monitoring"),
            "https://docs.spring.io/spring-framework/reference/core/aop.html"));

        frameworks.add(createFramework("Docker & Testcontainers", "docker-testcontainers", "DevOps",
            "Tools for containerizing Java applications and writing reliable integration tests using ephemeral Docker containers.",
            "## Docker & Testcontainers\n\nContainerization has revolutionized deployment, and Testcontainers has revolutionized integration testing.\n\n### Docker in Java\nSpring Boot 2.3+ introduced Cloud Native Buildpacks support, allowing you to build Docker images without writing a `Dockerfile` using `mvn spring-boot:build-image`.\n\n### Testcontainers\nTestcontainers is a Java library that supports JUnit tests, providing lightweight, throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container.\n- Eliminates the \"it works on my machine\" problem for tests.\n- No need to maintain external databases for CI/CD.",
            List.of("Docker", "Containerization", "Integration Testing", "Ephemeral Databases", "CI/CD"),
            "### Getting Started\n\nAdd `testcontainers` dependencies and annotate tests with `@Testcontainers`.",
            List.of(new CodeExample("Integration Test with Testcontainers", "java",
                "import org.junit.jupiter.api.Test;\nimport org.springframework.boot.test.context.SpringBootTest;\nimport org.testcontainers.containers.PostgreSQLContainer;\nimport org.testcontainers.junit.jupiter.Container;\nimport org.testcontainers.junit.jupiter.Testcontainers;\nimport org.springframework.test.context.DynamicPropertyRegistry;\nimport org.springframework.test.context.DynamicPropertySource;\n\n@SpringBootTest\n@Testcontainers\nclass UserRepositoryIntegrationTest {\n\n    // Starts a real PostgreSQL container before tests run\n    @Container\n    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(\"postgres:15-alpine\")\n        .withDatabaseName(\"testdb\")\n        .withUsername(\"test\")\n        .withPassword(\"test\");\n\n    // Dynamically wire the container URL into Spring Boot properties\n    @DynamicPropertySource\n    static void configureProperties(DynamicPropertyRegistry registry) {\n        registry.add(\"spring.datasource.url\", postgres::getJdbcUrl);\n        registry.add(\"spring.datasource.username\", postgres::getUsername);\n        registry.add(\"spring.datasource.password\", postgres::getPassword);\n    }\n\n    @Autowired\n    UserRepository userRepository;\n\n    @Test\n    void testSaveAndRetrieve() {\n        // Uses the real PostgreSQL database in the container\n        User u = userRepository.save(new User(\"testuser\"));\n        assertNotNull(u.getId());\n    }\n}",
                "Testcontainers spin up a real database in a Docker container during tests, ensuring your tests verify actual database behavior, not just mocks.")),
            List.of("Integration Testing", "Local Development", "Containerized Deployment"),
            "https://testcontainers.com/"));

        frameworkRepo.saveAll(frameworks);
    }

    private Framework createFramework(String name, String slug, String category,
                                       String description, String overview,
                                       List<String> keyFeatures, String gettingStarted,
                                       List<CodeExample> examples, List<String> useCases,
                                       String officialUrl) {
        Framework f = new Framework();
        f.setName(name);
        f.setSlug(slug);
        f.setCategory(category);
        f.setDescription(description);
        f.setOverview(overview);
        f.setKeyFeatures(keyFeatures);
        f.setGettingStarted(gettingStarted);
        f.setCodeExamples(examples);
        f.setUseCases(useCases);
        f.setOfficialUrl(officialUrl);
        return f;
    }
}
