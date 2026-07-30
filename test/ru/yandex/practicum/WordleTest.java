package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exceptions.WordNotFoundInDictionaryException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    @Test
    void testGenerateFeedback() {
        assertEquals("+++++", WordleDictionary.generateFeedback("книга", "книга"));
        assertEquals("^^^^^", WordleDictionary.generateFeedback("акниг", "книга"));
        assertEquals("^^-^^", WordleDictionary.generateFeedback("нкниг", "книга"));
    }

    @Test
    void testGameWithFixedAnswer() throws Exception {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга"));
        WordleGame testGame = new WordleGame(dict, "книга");
        assertEquals("книга", testGame.getAnswer());

        String feedback = testGame.makeGuess("книга");
        assertEquals("+++++", feedback);
        assertTrue(testGame.isGameOver());
        assertTrue(testGame.isWon());
        assertEquals(5, testGame.getSteps());
    }

    @Test
    void testWrongGuessFixed() throws Exception {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга", "слово"));
        WordleGame game = new WordleGame(dict, "книга");

        String feedback = game.makeGuess("слово");
        assertEquals("-----", feedback);
        assertFalse(game.isGameOver());
        assertEquals(5, game.getSteps());
    }

    @Test
    void testGameOverAfterSixAttempts() throws Exception {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга", "слово"));
        WordleGame game = new WordleGame(dict, "книга");

        for (int i = 0; i < 6; i++) {
            game.makeGuess("слово");
        }
        assertTrue(game.isGameOver());
        assertFalse(game.isWon());
        assertEquals(0, game.getSteps());
    }

    @Test
    void testHint() throws Exception {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга", "слово", "абвгд"));
        WordleGame game = new WordleGame(dict, "книга");

        game.makeGuess("слово"); // feedback "-----"
        String hint = game.getHint();
        assertEquals("книга", hint);
    }

    @Test
    void testWordNotFound() {
        WordleDictionary dict = new WordleDictionary(Arrays.asList("книга"));
        WordleGame testGame = new WordleGame(dict, "книга");
        assertThrows(WordNotFoundInDictionaryException.class, () -> {
            testGame.makeGuess("несущ");
        });
    }
}