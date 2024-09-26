package edu.pro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainLab22 {
    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();
        execute(); //29.65
        //executeLines(); //30.89

        long finish = System.nanoTime();

        System.out.println("------");
        System.out.println("Execution Time: " + TimeUnit.NANOSECONDS.toMillis(finish - start) + "ms");

    }

    private static void execute() throws IOException {
        Pattern splitPattern = Pattern.compile("\\s+");
        Path path = Paths.get("src/edu/pro/txt/harry.txt");

        Map<String, Integer> dictionary = new HashMap<>();
        try (var reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                StringBuilder cleanedLine = clean(line);
                splitPattern.splitAsStream(cleanedLine).forEach(word -> {
                    if (!word.isEmpty()) {
                        word = word.intern();
                        dictionary.put(word, dictionary.getOrDefault(word, 0) + 1);
                    }
                });
            }
        }
        dictionary.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(30)
                .forEach(i -> System.out.println(i.getKey() + " " + i.getValue()));
    }

    private static void executeLines() throws IOException {
        Pattern splitPattern = Pattern.compile("\\s+");
        Path path = Paths.get("src/edu/pro/txt/harry.txt");
        try (Stream<String> lines = Files.lines(path)) {
            lines.map(MainLab22::clean)
                    .flatMap(splitPattern::splitAsStream)
                    .filter(word -> !word.isEmpty())
                    .map(String::intern)
                    .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(30)
                    .forEach(i -> System.out.println(i.getKey() + " " + i.getValue()));
        }

    }


    private static StringBuilder clean(String line) {
        char[] chars = line.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (Character.isLetter(c)) {
                if (Character.isUpperCase(c)) {
                    c = Character.toLowerCase(c);
                }
                sb.append(c);
            } else if (Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb;
    }
}
