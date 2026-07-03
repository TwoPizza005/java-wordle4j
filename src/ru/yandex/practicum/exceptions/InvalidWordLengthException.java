package ru.yandex.practicum.exceptions;

public class InvalidWordLengthException extends WordleException {
    public InvalidWordLengthException(String message) {
        super(message);
    }
}