package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.EmptyDictionaryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {

    public static WordleDictionary load(String filename) throws IOException, EmptyDictionaryException {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = normalize(line.trim());
                if (word.length() == 5 ) {
                    words.add(word);
                }
            }
        }

        if (words.isEmpty()) {
            throw new EmptyDictionaryException("Словарь не содержит подходящих слов (5 букв, русские).");
        }

        return new WordleDictionary(words);
    }

    private static String normalize(String word) {
        word = word.toLowerCase();
        return word.replace('ё', 'е');
    }
}