package src.test.spaceinvaders.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import src.main.spaceinvaders.config.GameConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest {

    @BeforeAll
    static void setUp() throws IOException {
        // Given - Créer le fichier de configuration de test si pas existant
        Path resourcesDir = Paths.get("resources/game.properties");
        Files.createDirectories(resourcesDir);

        Path propertiesFile = resourcesDir.resolve("game.properties");

        if (!Files.exists(propertiesFile)) {
            Properties testProps = new Properties();
            testProps.setProperty("window.width", "800");
            testProps.setProperty("window.height", "600");
            testProps.setProperty("ship.speed", "5");

            try (FileOutputStream out = new FileOutputStream(propertiesFile.toFile())) {
                testProps.store(out, "Test game configuration");
            }
        }
    }

    @Test
    @DisplayName("Should return correct window width when properties are loaded")
    void shouldReturnCorrectWindowWidthWhenPropertiesAreLoaded() {
        // Given - Le fichier de configuration est chargé au démarrage

        // When
        int width = GameConfig.getWindowWidth();

        // Then
        assertEquals(800, width);
    }

    @Test
    @DisplayName("Should return correct window height when properties are loaded")
    void shouldReturnCorrectWindowHeightWhenPropertiesAreLoaded() {
        // Given - Le fichier de configuration est chargé au démarrage

        // When
        int height = GameConfig.getWindowHeight();

        // Then
        assertEquals(600, height);
    }

    @Test
    @DisplayName("Should return correct ship speed when properties are loaded")
    void shouldReturnCorrectShipSpeedWhenPropertiesAreLoaded() {
        // Given - Le fichier de configuration est chargé au démarrage

        // When
        int speed = GameConfig.getShipSpeed();

        // Then
        assertEquals(5, speed);
    }

    @Test
    @DisplayName("Should calculate ship start X position centered when ship width is provided")
    void shouldCalculateShipStartXPositionCenteredWhenShipWidthProvided() {
        // Given
        int shipWidth = 50;
        int expectedX = (800 - 50) / 2; // (windowWidth - shipWidth) / 2

        // When
        int startX = GameConfig.getShipStartX(shipWidth);

        // Then
        assertEquals(expectedX, startX);
        assertEquals(375, startX);
    }

    @Test
    @DisplayName("Should calculate enemies grid start X position centered when grid width is provided")
    void shouldCalculateEnemiesGridStartXPositionCenteredWhenGridWidthProvided() {
        // Given
        int gridWidth = 300;
        int expectedX = (800 - 300) / 2; // (windowWidth - gridWidth) / 2

        // When
        int startX = GameConfig.getEnemiesGridStartX(gridWidth);

        // Then
        assertEquals(expectedX, startX);
        assertEquals(250, startX);
    }

    @Test
    @DisplayName("Should center ship at exact middle when ship width equals window width")
    void shouldCenterShipAtExactMiddleWhenShipWidthEqualsWindowWidth() {
        // Given
        int shipWidth = 800;

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
        int expectedX = 800 / 2;

        // When
        int startX = GameConfig.getShipStartX(width);

        // Then
        assertEquals(expectedX, startX);
    }
}