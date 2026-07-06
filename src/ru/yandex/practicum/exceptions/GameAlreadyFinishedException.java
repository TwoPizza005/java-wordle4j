package ru.yandex.practicum.exceptions;

public class GameAlreadyFinishedException extends WordleException {
    public GameAlreadyFinishedException(String message) {
        super(message);
    }
}