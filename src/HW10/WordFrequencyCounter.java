package HW10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordFrequencyCounter {
    public static void main(String[] args) {
        String filePath = "words.txt";
        Map<String, Integer> wordFrequencies = calculateWordFrequencies(filePath);
        printWordFrequencies(wordFrequencies);
    }

    private static Map<String, Integer> calculateWordFrequencies(String filePath) {
        Map<String, Integer> wordFrequencies = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                wordFrequencies.merge(word, 1, Integer::sum);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return wordFrequencies;
    }

    private static void printWordFrequencies(Map<String, Integer> wordFrequencies) {
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}