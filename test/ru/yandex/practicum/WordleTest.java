package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void testGenerateFeedback() {
        assertEquals("+++++", WordleDictionary.generateFeedback("книга", "книга"));
        assertEquals("^----", WordleDictionary.generateFeedback("акниг", "книга"));
        assertEquals("-^---", WordleDictionary.generateFeedback("нкниг", "книга"));
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
        WordleDictionary singleDict = new WordleDictionary(Arrays.asList("книга"));
        WordleGame testGame = new WordleGame(singleDict);
        String feedback = testGame.makeGuess("абвгд");
        assertEquals("-----", feedback);
        assertFalse(testGame.isGameOver());
        assertEquals(5, testGame.getSteps());
    }

    @Test
    void testWordNotFound() {
        WordleDictionary singleDict = new WordleDictionary(Arrays.asList("книга"));
        WordleGame testGame = new WordleGame(singleDict);
        assertThrows(WordNotFoundInDictionaryException.class, () -> {
            testGame.makeGuess("несущ");
        });
    }

    @Test
    void testGameOverAfterSixAttempts() throws Exception {
        WordleDictionary singleDict = new WordleDictionary(Arrays.asList("книга"));
        WordleGame testGame = new WordleGame(singleDict);
        for (int i = 0; i < 6; i++) {
            testGame.makeGuess("абвгд");
        }
        assertTrue(testGame.isGameOver());
        assertFalse(testGame.isWon());
        assertEquals(0, testGame.getSteps());
    }

    @Test
    void testHint() throws Exception {
        WordleDictionary multiDict = new WordleDictionary(Arrays.asList("абвгд", "книга", "слово"));
        WordleGame testGame = new WordleGame(multiDict);
        // В тесте можно не проверять конкретное слово, а только наличие подсказки
        assertNotNull(testGame.getHint());
    }
}
