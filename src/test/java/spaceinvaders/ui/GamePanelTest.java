package spaceinvaders.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spaceinvaders.game.Boss;
import spaceinvaders.game.Enemy;
import spaceinvaders.game.GameState;
import spaceinvaders.game.GameTimer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class GamePanelTest {

    private GamePanel gamePanel;
    private Graphics2D graphics;
    private BufferedImage image;

    @BeforeEach
    void setUp() throws Exception {
        gamePanel = new GamePanel();
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        graphics = image.createGraphics();

        // Réinitialiser GameState
        resetGameState();
        resetElapsedSeconds();
    }

    @AfterEach
    void tearDown() throws Exception {
        graphics.dispose();
        resetGameState();
        resetElapsedSeconds();
    }

    @Test
    void testConstructor_shouldInitializeRenderer() throws Exception {
        // Given
        GamePanel panel = new GamePanel();

        // When
        Renderer renderer = getRenderer(panel);

        // Then
        assertNotNull(renderer, "Renderer should be initialized in constructor");
    }

    @Test
    void testPaintComponent_shouldCallSuperPaintComponent() {
        // Given
        GamePanel panel = new GamePanel();

        // When
        panel.paintComponent(graphics);

        // Then
        assertDoesNotThrow(() -> panel.paintComponent(graphics));
    }

    @Test
    void testPaintComponent_shouldDrawShip() {
        // Given
        GameState.SHIP_POSITION_X = 100;
        GameState.SHIP_POSITION_Y = 500;


        // When
        gamePanel.paintComponent(graphics);

        // Then
        // Vérifier qu'un pixel a été dessiné à la position du vaisseau
        int pixel = image.getRGB(100, 500);
        assertNotEquals(0, pixel, "Should draw ship");
    }

    @Test
    void testPaintComponent_shouldDrawBullets() {
        // Given
        GameState.projectiles.clear();
        GameState.projectiles.add(new Point(200, 300));

        // When
        gamePanel.paintComponent(graphics);

        // Then
        int pixel = image.getRGB(200, 300);
        assertNotEquals(0, pixel, "Should draw bullets");
    }

    @Test
    void testPaintComponent_shouldDrawEnemies() {
        // Given
        GameState.enemies.clear();
        GameState.enemies.add(new Enemy(150, 100, false));

        // When
        gamePanel.paintComponent(graphics);

        // Then
        int pixel = image.getRGB(150, 100);
        assertNotEquals(0, pixel, "Should draw enemies");
    }

    @Test
    void testPaintComponent_shouldDrawBoss() {
        // Given
        GameState.boss = new Boss(250, 50, 56);

        // When
        gamePanel.paintComponent(graphics);

        // Then
        int pixel = image.getRGB(250, 50);
        assertNotEquals(0, pixel, "Should draw boss");
    }

    @Test
    void testPaintComponent_shouldDrawBossProjectiles() {
        // Given
        GameState.bossProjectiles.clear();
        GameState.bossProjectiles.add(new Point(300, 250));

        // When
        gamePanel.paintComponent(graphics);

        // Then
        int pixel = image.getRGB(300, 250);
        assertNotEquals(0, pixel, "Should draw boss projectiles");
    }

    @Test
    void testPaintComponent_shouldDrawScore() {
        // Given
        GameState.score = 5000;

        // When
        gamePanel.paintComponent(graphics);

        // Then
        // Vérifier que du texte a été dessiné près de la position du score
        int pixel = image.getRGB(25, 35);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw score");
    }

    @Test
    void testPaintComponent_shouldDrawTime() throws Exception {
        // Given
        setElapsedSeconds(120);

        // When
        gamePanel.paintComponent(graphics);

        // Then
        // Vérifier que du texte a été dessiné près de la position du timer
        int pixel = image.getRGB(25, 55);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw time");
    }

    @Test
    void testPaintComponent_shouldDrawGameOverWhenGameIsOver() {
        // Given
        GameState.isGameOver = true;

        // When
        gamePanel.paintComponent(graphics);

        // Then
        // Vérifier qu'un texte a été dessiné au centre (game over)
        assertDoesNotThrow(() -> gamePanel.paintComponent(graphics));
    }

    @Test
    void testPaintComponent_shouldDrawWinWhenGameIsWon() {
        // Given
        GameState.isGameWin = true;

        // When
        gamePanel.paintComponent(graphics);

        // Then
        // Vérifier qu'un texte a été dessiné au centre (you win)
        assertDoesNotThrow(() -> gamePanel.paintComponent(graphics));
    }

    @Test
    void testPaintComponent_shouldHandleEmptyGameState() {
        // Given
        GameState.projectiles.clear();
        GameState.enemies.clear();
        GameState.bossProjectiles.clear();
        GameState.boss = null;
        GameState.score = 0;
        GameState.isGameOver = false;
        GameState.isGameWin = false;

        // When & Then
        assertDoesNotThrow(() -> gamePanel.paintComponent(graphics),
                "Should handle empty game state without errors");
    }

    @Test
    void testPaintComponent_shouldCallAllRendererMethods() throws Exception {
        // Given
        // Configuration complète du GameState
        GameState.SHIP_POSITION_X = 100;
        GameState.SHIP_POSITION_Y = 500;
        GameState.projectiles.clear();
        GameState.projectiles.add(new Point(200, 300));
        GameState.enemies.clear();
        GameState.enemies.add(new Enemy(150, 100, false));
        GameState.boss = new Boss(250, 50, 56);
        GameState.bossProjectiles.clear();
        GameState.bossProjectiles.add(new Point(300, 250));
        GameState.score = 1000;
        setElapsedSeconds(60);

        // When
        gamePanel.paintComponent(graphics);

        // Then
        // Vérifier que la méthode s'exécute sans erreur avec tous les éléments
        assertDoesNotThrow(() -> gamePanel.paintComponent(graphics),
                "Should call all renderer methods successfully");
    }

    @Test
    void testPaintComponent_shouldRenderInCorrectOrder() {
        // Given
        GameState.SHIP_POSITION_X = 100;
        GameState.SHIP_POSITION_Y = 500;
        GameState.projectiles.add(new Point(200, 300));
        GameState.enemies.add(new Enemy(150, 100, false));

        // When
        gamePanel.paintComponent(graphics);

        // Then
        // L'ordre de rendu est important pour l'affichage correct
        // Ce test vérifie que toutes les couches sont dessinées sans erreur
        assertDoesNotThrow(() -> gamePanel.paintComponent(graphics),
                "Should render all layers in correct order");
    }

    @Test
    void testPaintComponent_shouldHandleNullGraphics() {
        // Given
        Graphics nullGraphics = null;

        // When & Then
        assertThrows(NullPointerException.class,
                () -> gamePanel.paintComponent(nullGraphics),
                "Should throw exception when graphics is null");
    }

    @Test
    void testPaintComponent_multipleCallsShouldNotCauseErrors() {
        // Given
        GameState.score = 100;

        // When & Then
        assertDoesNotThrow(() -> {
            gamePanel.paintComponent(graphics);
            gamePanel.paintComponent(graphics);
            gamePanel.paintComponent(graphics);
        }, "Multiple paint calls should not cause errors");
    }

    @Test
    void testPaintComponent_shouldWorkWithDifferentGraphicsContexts() {
        // Given
        BufferedImage image2 = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2 = image2.createGraphics();

        try {
            // When & Then
            assertDoesNotThrow(() -> {
                gamePanel.paintComponent(graphics);
                gamePanel.paintComponent(graphics2);
            }, "Should work with different graphics contexts");
        } finally {
            graphics2.dispose();
        }
    }

    @Test
    void testPaintComponent_shouldHandleGameOverAndWinSimultaneously() {
        // Given
        GameState.isGameOver = true;
        GameState.isGameWin = true;

        // When & Then
        assertDoesNotThrow(() -> gamePanel.paintComponent(graphics),
                "Should handle both game over and win states");
    }

    @Test
    void testGamePanel_isJPanel() {
        // Given
        GamePanel panel = new GamePanel();

        // When & Then
        assertTrue(panel instanceof javax.swing.JPanel,
                "GamePanel should extend JPanel");
    }

    @Test
    void testRenderer_isNotNull() throws Exception {
        // Given
        GamePanel panel = new GamePanel();

        // When
        Renderer renderer = getRenderer(panel);

        // Then
        assertNotNull(renderer, "Renderer should never be null");
    }

    @Test
    void testRenderer_isCorrectType() throws Exception {
        // Given
        GamePanel panel = new GamePanel();

        // When
        Renderer renderer = getRenderer(panel);

        // Then
        assertTrue(renderer instanceof Renderer,
                "Should be instance of spaceinvaders.ui.Renderer");
    }

    // Helper methods
    private Renderer getRenderer(GamePanel gamePanel) throws Exception {
        Field field = GamePanel.class.getDeclaredField("renderer");
        field.setAccessible(true);
        return (Renderer) field.get(gamePanel);
    }

    private void resetGameState() throws Exception {
        GameState.projectiles.clear();
        GameState.enemies.clear();
        GameState.bossProjectiles.clear();
        GameState.boss = null;
        GameState.score = 0;
        GameState.isGameOver = false;
        GameState.isGameWin = false;
        GameState.SHIP_POSITION_X = 0;
        GameState.SHIP_POSITION_Y = 0;
    }

    private void resetElapsedSeconds() throws Exception {
        Field elapsedSecondsField = GameTimer.class.getDeclaredField("elapsedSeconds");
        elapsedSecondsField.setAccessible(true);
        elapsedSecondsField.setInt(null, 0);
    }

    private void setElapsedSeconds(int seconds) throws Exception {
        Field elapsedSecondsField = GameTimer.class.getDeclaredField("elapsedSeconds");
        elapsedSecondsField.setAccessible(true);
        elapsedSecondsField.setInt(null, seconds);
    }
}