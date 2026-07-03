package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;
import ru.yandex.practicum.exceptions.InvalidWordLengthException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleGame {

    private String answer;
    private int steps;
    private final WordleDictionary dictionary;

    private boolean gameOver;
    private boolean won;
    private final List<GameHistoryEntry> history;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
        this.gameOver = false;
        this.won = false;
        this.history = new ArrayList<>();
    }

    public int getSteps() {
        return steps;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWon() {
        return won;
    }

    public String getAnswer() {
        return answer;
    }

    public List<GameHistoryEntry> getHistory() {
        return new ArrayList<>(history);
    }

    public String makeGuess(String guess) throws WordNotFoundInDictionaryException, InvalidWordLengthException {
        if (gameOver) {
            throw new IllegalStateException("Игра уже завершена.");
        }

        if (guess.length() != 5) {
            throw new InvalidWordLengthException("Слово должно состоять из 5 букв.");
        }

        if (!dictionary.contains(guess)) {
            throw new WordNotFoundInDictionaryException("Слово \"" + guess + "\" не найдено в словаре.");
        }

        String feedback = WordleDictionary.generateFeedback(guess, answer);
        history.add(new GameHistoryEntry(guess, feedback));

        steps--;

        if (feedback.equals("+++++")) {
            gameOver = true;
            won = true;
        } else if (steps == 0) {
            gameOver = true;
            won = false;
        }

        return feedback;
    }

    public String getHint() {
        if (gameOver) {
            return null;
        }
        List<String> candidates = dictionary.filterByHistory(history);
        if (candidates.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return candidates.get(random.nextInt(candidates.size()));
    }
}