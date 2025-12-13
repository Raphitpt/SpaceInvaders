package spaceinvaders.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spaceinvaders.config.GameConfig;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @BeforeEach
    void setup() {
        // reset a chaque testss
        GameState.reset();
    }

    @Test
    void moveShip_shouldUpdateShipPositionXCorrectly_positiveDelta() {
        // Given
        int initialX = GameState.SHIP_POSITION_X;
        int deltaX = 5;

        // When
        GameState.moveShip(deltaX);

        // Then
        assertEquals(initialX + deltaX, GameState.SHIP_POSITION_X, "La position X du vaisseau devrait avoir augmenté.");
    }

    @Test
    void moveShip_shouldUpdateShipPositionXCorrectly_negativeDelta() {
        // Given
        int initialX = GameState.SHIP_POSITION_X;
        int deltaX = -10;

        // When
        GameState.moveShip(deltaX);

        // Then
        assertEquals(initialX + deltaX, GameState.SHIP_POSITION_X, "La position X du vaisseau devrait avoir diminué.");
    }


    @Test
    void reset_shouldClearProjectilesAndEnemies() {
        // Given
        GameState.projectiles.add(new Point(10, 10));
        GameState.bossProjectiles.add(new Point(20, 20));
        GameState.enemies.add(new Enemy(1, 1, false));
        GameState.boss = new Boss(300, 50, 60);
        GameState.isBossSpawned = true;

        // When
        GameState.reset();

        // Then
        assertTrue(GameState.projectiles.isEmpty(), "La liste de projectiles devrait être vide.");
        assertTrue(GameState.bossProjectiles.isEmpty(), "La liste de projectiles du boss devrait être vide.");
        assertTrue(GameState.enemies.isEmpty(), "La liste d'ennemis devrait être vide.");
        assertNull(GameState.boss, "Le boss devrait être null.");
        assertFalse(GameState.isBossSpawned, "isBossSpawned devrait être false.");
    }

    @Test
    void reset_shouldResetGameStatusAndCounters() {
        // Given
        GameState.score = 500;
        GameState.elapsedTicks = 1000;
        GameState.isGameOver = true;
        GameState.isGameWin = true;

        // When
        GameState.reset();

        // Then
        assertEquals(0, GameState.score, "Le score devrait être réinitialisé à 0.");
        assertEquals(0, GameState.elapsedTicks, "elapsedTicks devrait être réinitialisé à 0.");
        assertFalse(GameState.isGameOver, "isGameOver devrait être false.");
        assertFalse(GameState.isGameWin, "isGameWin devrait être false.");
    }

    @Test
    void reset_shouldResetShipPosition() {
        // Given
        GameState.SHIP_POSITION_X = 100;
        int expectedStartX = GameConfig.getShipStartX(GameState.SHIP_WIDTH);

        // When
        GameState.reset();

        // Then
        assertEquals(expectedStartX, GameState.SHIP_POSITION_X, "La position X du vaisseau devrait être réinitialisée par GameConfig.");
        assertEquals(450, GameState.SHIP_POSITION_Y, "La position Y du vaisseau devrait être réinitialisée à 450.");
    }

    @Test
    void initEnemies_shouldPopulateEnemiesListWithCorrectCount() {
        // Given: Un état de jeu propre (via @BeforeEach/reset)

        // When: La méthode initEnemies est appelée
        GameState.initEnemies();

        // Then: La liste d'ennemis doit contenir 50 ennemis (5 * 10)
        assertEquals(50, GameState.enemies.size(), "La liste d'ennemis devrait contenir 50 ennemis.");
    }

    @Test
    void initEnemies_shouldContainAtLeastOneEnemyOrBoss() {
        // Given: Un état de jeu propre

        // When: La méthode initEnemies est appelée
        GameState.initEnemies();

        // Then: La liste ne doit pas être vide
        assertFalse(GameState.enemies.isEmpty(), "La liste d'ennemis ne devrait pas être vide.");

        List<Enemy> enemies = GameState.enemies;
        long bossCount = enemies.stream().filter(enemy -> enemy.isBoss).count();

        assertTrue(bossCount > 1, "Il existe bien un ennemi boss dans la liste d'enemis");
        assertNotNull(enemies.get(0), "Le premier ennemi ne devrait pas être null.");
    }
}