package ru.yandex.practicum;

import java.util.*;

public class WordleDictionary {

    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public List<String> filterByHistory(List<GameHistoryEntry> history) {
        List<String> candidates = new ArrayList<>(words);
        for (GameHistoryEntry entry : history) {
            candidates.removeIf(word -> !matchesFeedback(word, entry.guess, entry.feedback));
        }
        return candidates;
    }

    private boolean matchesFeedback(String candidate, String guess, String feedback) {
        String generated = generateFeedback(guess, candidate);
        return generated.equals(feedback);
    }

    public static String generateFeedback(String guess, String answer) {
        if (guess.length() != 5 || answer.length() != 5) {
            throw new IllegalArgumentException("Слова должны быть длиной 5");
        }
        char[] result = new char[5];
        boolean[] usedInAnswer = new boolean[5];
        boolean[] usedInGuess = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                result[i] = '+';
                usedInAnswer[i] = true;
                usedInGuess[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (usedInGuess[i]) continue;
            char c = guess.charAt(i);
            for (int j = 0; j < 5; j++) {
                if (!usedInAnswer[j] && c == answer.charAt(j)) {
                    result[i] = '^';
                    usedInAnswer[j] = true;
                    usedInGuess[i] = true;
                    break;
                }
            }
            if (!usedInGuess[i]) {
                result[i] = '-';
            }
        }
        return new String(result);
    }
}