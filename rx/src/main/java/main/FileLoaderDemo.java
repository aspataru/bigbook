package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class FileLoaderDemo {

    // preloading the file defeats the purpose of reader.lines()
    private Queue<String> preloadedLines = new ArrayDeque<>();

    public FileLoaderDemo(String dataFilePath) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            reader.lines().forEach(line -> preloadedLines.offer(line));
        }
    }

    public long countLinesOfFile() {
        return preloadedLines.stream()
                .filter(line -> !line.startsWith("#"))
                // .peek(System.out::println)
                .count();
    }

}
