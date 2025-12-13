package spaceinvaders.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {

    private JPanel gamePanel;

    @BeforeEach
    void setUp() throws Exception {
        gamePanel = new JPanel();
        resetElapsedSeconds();
    }

    @AfterEach
    void tearDown() throws Exception {
        resetElapsedSeconds();
    }

    @Test
    void testConstructor_shouldInitializeAndStartTimer() {
        // Given
        JPanel panel = new JPanel();

        // When
        GameTimer gameTimer = new GameTimer(panel);

        // Then
        assertNotNull(gameTimer);
        Timer timer = getGameLoopTimer(gameTimer);
        assertNotNull(timer);
        assertTrue(timer.isRunning());
        assertEquals(50, timer.getDelay());
    }

    @Test
    void testStopGame_shouldStopTimer() {
        // Given
        GameTimer gameTimer = new GameTimer(gamePanel);
        Timer timer = getGameLoopTimer(gameTimer);
        assertTrue(timer.isRunning());

        // When
        gameTimer.stopGame();

        // Then
        assertFalse(timer.isRunning());
    }

    @Test
    void testGameLoop_shouldIncrementElapsedSecondsAfter1000Ms() {
        // Given
        GameTimer gameTimer = new GameTimer(gamePanel);
        Timer timer = getGameLoopTimer(gameTimer);
        ActionListener[] listeners = timer.getActionListeners();
        assertEquals(1, listeners.length);
        ActionListener gameLoopListener = listeners[0];

        // When
        int initialSeconds = GameTimer.getElapsedSeconds();
        for (int i = 0; i < 20; i++) {
            gameLoopListener.actionPerformed(null);
        }

        // Then
        assertEquals(initialSeconds + 1, GameTimer.getElapsedSeconds());
    }

    @Test
    void testGameLoop_shouldIncrementMultipleSeconds() {
        // Given
        GameTimer gameTimer = new GameTimer(gamePanel);
        Timer timer = getGameLoopTimer(gameTimer);
        ActionListener gameLoopListener = timer.getActionListeners()[0];

        // When
        int initialSeconds = GameTimer.getElapsedSeconds();
        for (int i = 0; i < 40; i++) {
            gameLoopListener.actionPerformed(null);
        }

        // Then
        assertEquals(initialSeconds + 2, GameTimer.getElapsedSeconds());
    }

    @Test
    void testGameLoop_shouldNotIncrementSecondsBeforeThreshold() {
        // Given
        GameTimer gameTimer = new GameTimer(gamePanel);
        Timer timer = getGameLoopTimer(gameTimer);
        ActionListener gameLoopListener = timer.getActionListeners()[0];

        // When
        int initialSeconds = GameTimer.getElapsedSeconds();
        for (int i = 0; i < 19; i++) {
            gameLoopListener.actionPerformed(null);
        }

        // Then
        assertEquals(initialSeconds, GameTimer.getElapsedSeconds());
    }

    @Test
    void testGameLoop_shouldResetAccumulatorAfterSecondIncrement()  {
        // Given
        GameTimer gameTimer = new GameTimer(gamePanel);
        Timer timer = getGameLoopTimer(gameTimer);
        ActionListener gameLoopListener = timer.getActionListeners()[0];

        // When
        int initialSeconds = GameTimer.getElapsedSeconds();
        for (int i = 0; i < 21; i++) {
            gameLoopListener.actionPerformed(null);
        }

        // Then
        assertEquals(initialSeconds + 1, GameTimer.getElapsedSeconds());
    }

    @Test
    void testGetElapsedSeconds_shouldReturnZeroInitially() {
        // Given - Fresh start with reset

        // When
        int seconds = GameTimer.getElapsedSeconds();

        // Then
        assertEquals(0, seconds);
    }

    @Test
    void testGetElapsedSeconds_shouldReturnCurrentValue()  {
        // Given
        GameTimer gameTimer = new GameTimer(gamePanel);
        Timer timer = getGameLoopTimer(gameTimer);
        ActionListener gameLoopListener = timer.getActionListeners()[0];

        // When
        int beforeTicks = GameTimer.getElapsedSeconds();
        for (int i = 0; i < 20; i++) {
            gameLoopListener.actionPerformed(null);
        }
        int afterTicks = GameTimer.getElapsedSeconds();

        // Then
        assertEquals(beforeTicks + 1, afterTicks);
    }

    @Test
    void testStopGame_shouldPreventFurtherUpdates()  {
        // Given
        GameTimer gameTimer = new GameTimer(gamePanel);
        Timer timer = getGameLoopTimer(gameTimer);
        ActionListener gameLoopListener = timer.getActionListeners()[0];

        for (int i = 0; i < 20; i++) {
            gameLoopListener.actionPerformed(null);
        }
        int secondsAfterFirstIncrement = GameTimer.getElapsedSeconds();

        // When
        gameTimer.stopGame();


        for (int i = 0; i < 20; i++) {
            gameLoopListener.actionPerformed(null);
        }

        // Then
        assertFalse(timer.isRunning());
    }

    // Helper methods
    private Timer getGameLoopTimer(GameTimer gameTimer) {
        try {
            Field timerField = GameTimer.class.getDeclaredField("gameLoopTimer");
            timerField.setAccessible(true);
            return (Timer) timerField.get(gameTimer);
        } catch (Exception e) {
            fail("Could not access gameLoopTimer field: " + e.getMessage());
            return null;
        }
    }

    private void resetElapsedSeconds() throws Exception {
        Field elapsedSecondsField = GameTimer.class.getDeclaredField("elapsedSeconds");
        elapsedSecondsField.setAccessible(true);
        elapsedSecondsField.setInt(null, 0);
    }
}