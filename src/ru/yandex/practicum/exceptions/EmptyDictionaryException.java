package ru.yandex.practicum.exceptions;

public class EmptyDictionaryException extends WordleException {
    public EmptyDictionaryException(String message) {
        super(message);
    }
}