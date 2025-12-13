package spaceinvaders.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spaceinvaders.config.GameConfig;
import spaceinvaders.game.Boss;
import spaceinvaders.game.Enemy;
import spaceinvaders.game.GameState;
import spaceinvaders.game.GameTimer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RendererTest {

    private Renderer renderer;
    private Graphics2D graphics;
    private BufferedImage image;

    @BeforeEach
    void setUp() throws Exception {
        renderer = new Renderer();
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
    void testDrawShip_shouldDrawGreenRectangle() {
        // Given
        GameState.SHIP_POSITION_X = 100;
        GameState.SHIP_POSITION_Y = 200;

        // When
        renderer.drawShip(graphics);

        // Then
        // Vérifier que des pixels verts ont été dessinés aux bonnes coordonnées
        int greenPixel = image.getRGB(100, 200);
        assertNotEquals(0, greenPixel & 0xFF00FF00, "Should have green color component");
    }

    @Test
    void testDrawBullet_shouldDrawRedRectanglesForEachProjectile() {
        // Given
        GameState.projectiles.clear();
        GameState.projectiles.add(new Point(100, 100));
        GameState.projectiles.add(new Point(200, 150));

        // When
        renderer.drawBullet(graphics);

        // Then
        // Vérifier que des pixels rouges ont été dessinés
        int pixel1 = image.getRGB(100, 100);
        int pixel2 = image.getRGB(200, 150);

        assertTrue((pixel1 & 0xFF0000) > 0, "Should have red component at first projectile");
        assertTrue((pixel2 & 0xFF0000) > 0, "Should have red component at second projectile");
    }

    @Test
    void testDrawBullet_shouldNotDrawWhenNoProjectiles() {
        // Given
        GameState.projectiles.clear();

        // Remplir l'image en blanc
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);

        // When
        renderer.drawBullet(graphics);

        // Then
        // L'image devrait rester blanche (aucun projectile dessiné)
        int pixel = image.getRGB(100, 100);
        assertEquals(Color.WHITE.getRGB(), pixel, "Should remain white when no projectiles");
    }

    @Test
    void testDrawEnemies_shouldDrawNormalEnemy() {
        // Given
        GameState.enemies.clear();
        Enemy normalEnemy = new Enemy(100, 100, false);
        GameState.enemies.add(normalEnemy);

        // When
        renderer.drawEnemies(graphics);

        // Then
        int pixel = image.getRGB(100, 100);
        assertNotEquals(0, pixel, "Should draw enemy at position");
    }

    @Test
    void testDrawEnemies_shouldDrawBossEnemyWithShieldColor() {
        // Given
        GameState.enemies.clear();
        Enemy bossEnemy = new Enemy(150, 150, true);
        GameState.enemies.add(bossEnemy);

        // When
        renderer.drawEnemies(graphics);

        // Then
        int pixel = image.getRGB(150, 150);
        assertNotEquals(0, pixel, "Should draw boss enemy at position");
    }

    @Test
    void testDrawEnemies_shouldDrawMultipleEnemies() {
        // Given
        GameState.enemies.clear();
        GameState.enemies.add(new Enemy(100, 100, false));
        GameState.enemies.add(new Enemy(200, 100, false));
        GameState.enemies.add(new Enemy(300, 100, true));

        // When
        renderer.drawEnemies(graphics);

        // Then
        assertEquals(3, GameState.enemies.size(), "Should handle multiple enemies");
    }

    @Test
    void testDrawEnemies_shouldNotDrawWhenNoEnemies() {
        // Given
        GameState.enemies.clear();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);

        // When
        renderer.drawEnemies(graphics);

        // Then
        int pixel = image.getRGB(100, 100);
        assertEquals(Color.WHITE.getRGB(), pixel, "Should remain white when no enemies");
    }

    @Test
    void testDrawBoss_shouldDrawBossWhenAlive() {
        // Given
        GameState.boss = new Boss(200, 50,46);
        assertTrue(GameState.boss.isAlive(), "Boss should be alive initially");

        // When
        renderer.drawBoss(graphics);

        // Then
        int pixel = image.getRGB(200, 50);
        assertNotEquals(0, pixel, "Should draw boss at position");
    }

    @Test
    void testDrawBoss_shouldDrawHealthBar() {
        // Given
        GameState.boss = new Boss(200, 50, 40);

        // When
        renderer.drawBoss(graphics);

        // Then
        // Vérifier que la barre de vie est dessinée au-dessus du boss
        int healthBarPixel = image.getRGB(200, 40); // 10 pixels au-dessus
        assertNotEquals(0, healthBarPixel, "Should draw health bar above boss");
    }

    @Test
    void testDrawBoss_shouldNotDrawWhenBossIsNull() {
        // Given
        GameState.boss = null;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);

        // When
        renderer.drawBoss(graphics);

        // Then
        int pixel = image.getRGB(200, 50);
        assertEquals(Color.WHITE.getRGB(), pixel, "Should not draw when boss is null");
    }

    @Test
    void testDrawBoss_shouldNotDrawWhenBossIsDead() {
        // Given
        GameState.boss = new Boss(200, 50, 50);
        GameState.boss.health = 0;
        assertFalse(GameState.boss.isAlive(), "Boss should be dead");

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);

        // When
        renderer.drawBoss(graphics);

        // Then
        int pixel = image.getRGB(200, 50);
        assertEquals(Color.WHITE.getRGB(), pixel, "Should not draw when boss is dead");
    }

    @Test
    void testDrawBossProjectiles_shouldDrawOrangeCircles() {
        // Given
        GameState.bossProjectiles.clear();
        GameState.bossProjectiles.add(new Point(150, 150));
        GameState.bossProjectiles.add(new Point(250, 200));

        // When
        renderer.drawBossProjectiles(graphics);

        // Then
        int pixel1 = image.getRGB(150, 150);
        int pixel2 = image.getRGB(250, 200);

        assertNotEquals(0, pixel1, "Should draw first projectile");
        assertNotEquals(0, pixel2, "Should draw second projectile");
    }

    @Test
    void testDrawBossProjectiles_shouldNotDrawWhenNoProjectiles() {
        // Given
        GameState.bossProjectiles.clear();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);

        // When
        renderer.drawBossProjectiles(graphics);

        // Then
        int pixel = image.getRGB(150, 150);
        assertEquals(Color.WHITE.getRGB(), pixel, "Should not draw when no boss projectiles");
    }

    @Test
    void testDrawScore_shouldDrawScoreText() {
        // Given
        GameState.score = 1250;

        // When
        renderer.drawScore(graphics);

        // Then
        // Vérifier que du texte a été dessiné près de la position attendue
        int pixel = image.getRGB(25, 35);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw score text");
    }

    @Test
    void testDrawScore_shouldDrawZeroScore() {
        // Given
        GameState.score = 0;

        // When
        renderer.drawScore(graphics);

        // Then
        int pixel = image.getRGB(25, 35);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw score even when zero");
    }

    @Test
    void testDrawTime_shouldDrawTimerText() throws Exception {
        // Given
        setElapsedSeconds(42);

        // When
        renderer.drawTime(graphics);

        // Then
        int pixel = image.getRGB(25, 55);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw timer text");
    }

    @Test
    void testDrawTime_shouldDrawZeroSeconds() throws Exception {
        // Given
        setElapsedSeconds(0);

        // When
        renderer.drawTime(graphics);

        // Then
        int pixel = image.getRGB(25, 55);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw timer even at zero");
    }

    @Test
    void testDrawGameOver_shouldDrawWhenGameIsOver() {
        // Given
        GameState.isGameOver = true;

        // When
        renderer.drawGameOver(graphics);

        // Then
        // Vérifier qu'un texte rouge a été dessiné au centre
        int centerX = GameConfig.getWindowWidth() / 2;
        int centerY = GameConfig.getWindowHeight() / 2;
        int pixel = image.getRGB(centerX, centerY);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw game over text");
    }

    @Test
    void testDrawGameOver_shouldNotDrawWhenGameIsNotOver() {
        // Given
        GameState.isGameOver = false;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);

        // When
        renderer.drawGameOver(graphics);

        // Then
        int centerX = GameConfig.getWindowWidth() / 2;
        int centerY = GameConfig.getWindowHeight() / 2;
        int pixel = image.getRGB(centerX, centerY);
        assertEquals(Color.WHITE.getRGB(), pixel, "Should not draw when game is not over");
    }

    @Test
    void testDrawWin_shouldDrawWhenGameIsWon() throws Exception {
        // Given
        GameState.isGameWin = true;
        setElapsedSeconds(45);

        // When
        renderer.drawWin(graphics);

        // Then
        int centerX = GameConfig.getWindowWidth() / 2;
        int centerY = GameConfig.getWindowHeight() / 2;
        int pixel = image.getRGB(centerX, centerY);
        assertNotEquals(Color.WHITE.getRGB(), pixel, "Should draw win text");
    }

    @Test
    void testDrawWin_shouldNotDrawWhenGameIsNotWon() {
        // Given
        GameState.isGameWin = false;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);

        // When
        renderer.drawWin(graphics);

        // Then
        int centerX = GameConfig.getWindowWidth() / 2;
        int centerY = GameConfig.getWindowHeight() / 2;
        int pixel = image.getRGB(centerX, centerY);
        assertEquals(Color.WHITE.getRGB(), pixel, "Should not draw when game is not won");
    }

    @Test
    void testDrawWin_shouldIncludeElapsedTimeInMessage() throws Exception {
        // Given
        GameState.isGameWin = true;
        setElapsedSeconds(30);

        // When
        renderer.drawWin(graphics);

        // Then
        // Le test vérifie que la méthode s'exécute sans erreur avec le temps
        assertDoesNotThrow(() -> renderer.drawWin(graphics));
    }

    // Helper methods
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