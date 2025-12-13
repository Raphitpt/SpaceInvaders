package spaceinvaders.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest {

    @Test
    @DisplayName("Should return correct window width when properties are loaded")
    void shouldReturnCorrectWindowWidthWhenPropertiesAreLoaded() {
        // Given-When
        int width = GameConfig.getWindowWidth();

        // Then
        assertEquals(1000, width);
    }

    @Test
    @DisplayName("Should return correct window height when properties are loaded")
    void shouldReturnCorrectWindowHeightWhenPropertiesAreLoaded() {
        // Given-When
        int height = GameConfig.getWindowHeight();

        // Then
        assertEquals(600, height);
    }

    @Test
    @DisplayName("Should return correct ship speed when properties are loaded")
    void shouldReturnCorrectShipSpeedWhenPropertiesAreLoaded() {
        // Given-When
        int speed = GameConfig.getShipSpeed();

        // Then
        assertEquals(10, speed);
    }

    @Test
    @DisplayName("Should calculate ship start X position centered when ship width is provided")
    void shouldCalculateShipStartXPositionCenteredWhenShipWidthProvided() {
        // Given
        int shipWidth = 50;
        int expectedX = (1000 - 50) / 2;

        // When
        int startX = GameConfig.getShipStartX(shipWidth);

        // Then
        assertEquals(expectedX, startX);
        assertEquals(475, startX);
    }

    @Test
    @DisplayName("Should calculate enemies grid start X position centered when grid width is provided")
    void shouldCalculateEnemiesGridStartXPositionCenteredWhenGridWidthProvided() {
        // Given
        int gridWidth = 300;
        int expectedX = (1000 - 300) / 2;

        // When
        int startX = GameConfig.getEnemiesGridStartX(gridWidth);

        // Then
        assertEquals(expectedX, startX);
        assertEquals(350, startX);
    }

    @Test
    @DisplayName("Should center ship at exact middle when ship width equals window width")
    void shouldCenterShipAtExactMiddleWhenShipWidthEqualsWindowWidth() {
        // Given
        int shipWidth = 1000;

        // When
        int startX = GameConfig.getShipStartX(shipWidth);

        // Then
        assertEquals(0, startX);
    }

    @Test
    @DisplayName("Should handle zero width correctly when calculating start position")
    void shouldHandleZeroWidthCorrectlyWhenCalculatingStartPosition() {
        // Given
        int width = 0;
        int expectedX = 1000 / 2;

        // When
        int startX = GameConfig.getShipStartX(width);

        // Then
        assertEquals(expectedX, startX);
    }
}
