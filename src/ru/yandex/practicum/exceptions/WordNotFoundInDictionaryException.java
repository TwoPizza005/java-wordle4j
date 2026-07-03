package ru.yandex.practicum.exceptions;

public class WordNotFoundInDictionaryException extends WordleException {
    public WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}
