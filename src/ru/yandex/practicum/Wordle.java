package ru.yandex.practicum;

import ru.yandex.practicum.exceptions.InvalidWordLengthException;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;
import ru.yandex.practicum.exceptions.WordleException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {

    private static final String DICTIONARY_FILE = "words_ru.txt";
    private static final String LOG_FILE = "game.log";
    private static PrintWriter log;

    public static void main(String[] args) {
        try {
            log = new PrintWriter(new FileWriter(LOG_FILE, StandardCharsets.UTF_8, true));
        } catch (IOException e) {
            System.err.println("Не удалось создать лог-файл. Программа завершается.");
            e.printStackTrace();
            return;
        }

        try {
            runGame();
        } catch (Throwable t) {
            log.println("Критическая ошибка: " + t.toString());
            t.printStackTrace(log);
            log.flush();
            System.err.println("Произошла ошибка. Подробности записаны в лог. Завершение.");
        } finally {
            if (log != null) {
                log.close();
            }
        }
    }

    private static void runGame() throws WordleException, IOException {
        WordleDictionary dictionary;
        try {
            dictionary = WordleDictionaryLoader.load(DICTIONARY_FILE);
            log.println("Словарь загружен. Количество слов: " + dictionary.getWords().size());
        } catch (IOException e) {
            log.println("Ошибка загрузки словаря: " + e.getMessage());
            e.printStackTrace(log);
            log.flush();
            throw e;
        }

        WordleGame game = new WordleGame(dictionary);
        log.println("Новая игра. Загадано слово: " + game.getAnswer());
        log.flush();

        Scanner scanner = new Scanner(System.in);
        System.out.println(" Начинаем игру Wordle!");
        System.out.println("Угадайте слово из 5 букв. У вас 6 попыток.");
        System.out.println("Введите слово или нажмите Enter для подсказки.");

        while (!game.isGameOver()) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
            input = input.replace('ё', 'е');

            if (input.isEmpty()) {
                String hint = game.getHint();
                if (hint == null) {
                    System.out.println("Подсказок нет ");
                } else {
                    System.out.println("Подсказка: " + hint);
                }
                continue;
            }

            if (input.length() != 5) {
                System.out.println("Слово должно быть длиной ровно 5 букв.");
                continue;
            }

            try {
                String feedback = game.makeGuess(input);
                System.out.println(input);
                System.out.println(feedback);
                log.println("Ход: " + input + " -> " + feedback);
                log.flush();

                if (game.isGameOver()) {
                    if (game.isWon()) {
                        System.out.println("Поздравляю! Вы угадали слово!");
                    } else {
                        System.out.println("Попытки закончились. Загаданное слово: " + game.getAnswer());
                    }
                } else {
                    System.out.println("Осталось попыток: " + game.getSteps());
                }

            } catch (WordNotFoundInDictionaryException e) {
                System.out.println("Такого слова нет в словаре. Попробуйте другое.");
                log.println("Ошибка: " + e.getMessage());
                log.flush();
            } catch (InvalidWordLengthException e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Игра завершена.");
        log.println("Игра завершена. Ответ: " + game.getAnswer());
        log.flush();
    }
}
