package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void testGenerateFeedback() {
        assertEquals("+++++", WordleDictionary.generateFeedback("книга", "книга"));
        // Все буквы присутствуют, но не на своих местах
        assertEquals("^^^^^", WordleDictionary.generateFeedback("акниг", "книга"));
        // Для "нкниг" – две 'н', одна в ответе, поэтому только первая '^', вторая '-'
        assertEquals("^^-^^", WordleDictionary.generateFeedback("нкниг", "книга"));
    }

    @Test
    void testGameWithFixedAnswer() throws Exception {
        WordleDictionary singleDict = new WordleDictionary(Arrays.asList("книга"));
        WordleGame testGame = new WordleGame(singleDict);
        assertEquals("книга", testGame.getAnswer());

        String feedback = testGame.makeGuess("книга");
        assertEquals("+++++", feedback);
        assertTrue(testGame.isGameOver());
        assertTrue(testGame.isWon());
        assertEquals(5, testGame.getSteps());
    }

    @Test
    void testWrongGuess() throws Exception {

        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга", "абвгд"));
        WordleGame testGame = new WordleGame(dict);

    }

    // Исправленный тест с использованием рефлексии (для демонстрации)
    @Test
    void testWrongGuessFixed() throws Exception {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга", "абвгд"));
        WordleGame game = new WordleGame(dict);
        // Устанавливаем ответ "книга" через рефлексию
        java.lang.reflect.Field field = WordleGame.class.getDeclaredField("answer");
        field.setAccessible(true);
        field.set(game, "книга");

        String feedback = game.makeGuess("абвгд");
        assertEquals("-----", feedback);
        assertFalse(game.isGameOver());
        assertEquals(5, game.getSteps());
    }

    @Test
    void testGameOverAfterSixAttempts() throws Exception {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга", "абвгд"));
        WordleGame game = new WordleGame(dict);
        java.lang.reflect.Field field = WordleGame.class.getDeclaredField("answer");
        field.setAccessible(true);
        field.set(game, "книга");

        for (int i = 0; i < 6; i++) {
            game.makeGuess("абвгд");
        }
        assertTrue(game.isGameOver());
        assertFalse(game.isWon());
        assertEquals(0, game.getSteps());
    }

    @Test
    void testHint() throws Exception {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("абвгд", "книга", "слово"));
        WordleGame game = new WordleGame(dict);
        java.lang.reflect.Field field = WordleGame.class.getDeclaredField("answer");
        field.setAccessible(true);
        field.set(game, "книга");


        game.makeGuess("абвгд");
        String hint = game.getHint();
        assertEquals("слово", hint);
    }

    @Test
    void testWordNotFound() {
        WordleDictionary singleDict = new WordleDictionary(Arrays.asList("книга"));
        WordleGame testGame = new WordleGame(singleDict);
        assertThrows(WordNotFoundInDictionaryException.class, () -> {
            testGame.makeGuess("несущ");
        });
    }
}