package ru.yandex.practicum;

public class GameHistoryEntry {
    public final String guess;
    public final String feedback;

    public GameHistoryEntry(String guess, String feedback) {
        this.guess = guess;
        this.feedback = feedback;
    }
}