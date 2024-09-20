package edu.pro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainLab22 {
	public static void main(String[] args) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		long start = System.nanoTime();

		execute();

		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		long finish = System.nanoTime();

		System.out.println("------");
		System.out.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore) / 1_048_576.0 + "MB");
		System.out.println("Execution Time: " + TimeUnit.NANOSECONDS.toMillis(finish - start) + "ms");

	}

	private static void execute() throws IOException {
		String fileContent = Files.readString(Paths.get("src/edu/pro/txt/harry.txt"));
		String cleanedFileContent = fileContent.replaceAll("[^\\w\\s]", " ").toLowerCase(Locale.ROOT);
		HashMap<String, Integer> dictionary = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new StringReader(cleanedFileContent))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] words = line.split("\\s+");
				for (String word : words) {
					if (!word.isBlank()) {
						dictionary.put(word, dictionary.getOrDefault(word, 0) + 1);
					}
				}
//				Arrays.stream(words).forEach(word -> {
//					if (!word.isBlank()) {
//						dictionary.put(word, dictionary.getOrDefault(word, 0) + 1);
//					}
//				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		dictionary.entrySet().stream().sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue())).limit(30)
				.forEach(i -> System.out
						.println(new StringBuilder(i.getKey()).append(" ").append(i.getValue()).toString()));
	}
}
