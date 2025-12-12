package spaceinvaders.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import spaceinvaders.input.InputHandler;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    // Classe de test pour GameTimer (remplace le mock)
    static class TestGameTimer extends GameTimer {
        public boolean stopGameCalled = false;

        public TestGameTimer() {
            super(new javax.swing.JPanel()); // On crée un JPanel vide pour les tests
        }

        @Override
        public void stopGame() {
            stopGameCalled = true;
            // On n'appelle pas super.stopGame() pour éviter d'interagir avec le timer
        }

        public void reset() {
            stopGameCalled = false;
        }
    }

    // Classe de test pour InputHandler (remplace le mock)
    static class TestInputHandler extends InputHandler {
        public TestInputHandler() {
            super(new javax.swing.JPanel()); // On crée un JPanel vide pour les tests
        }

        @Override
        public void setupKeyboardListener() {
            // Ne fait rien dans les tests - on évite d'ajouter des listeners
        }
    }

    private GameEngine gameEngine;
    private TestGameTimer testGameTimer;
    private TestInputHandler testInputHandler;

    @BeforeEach
    void setUp() {
        GameState.reset();
        testGameTimer = new TestGameTimer();
        testInputHandler = new TestInputHandler();
        gameEngine = new GameEngine(testGameTimer, testInputHandler);
        testGameTimer.reset();
    }

    // ==================== Tests de updateProjectiles ====================

    @Test
    @DisplayName("Given projectile moving upward, When update, Then projectile y decreases")
    void testUpdateProjectiles_MovesUpward() {
        // Given
        Point projectile = new Point(100, 200);
        GameState.projectiles.add(projectile);

        // When
        gameEngine.update();

        // Then
        assertEquals(190, projectile.y);
    }

    @Test
    @DisplayName("Given projectile out of bounds, When update, Then projectile is removed")
    void testUpdateProjectiles_RemovesOutOfBounds() {
        // Given
        Point projectile = new Point(100, 5);
        GameState.projectiles.add(projectile);

        // When
        gameEngine.update();

        // Then
        assertFalse(GameState.projectiles.contains(projectile));
        assertTrue(GameState.projectiles.isEmpty());
    }

    @Test
    @DisplayName("Given multiple projectiles, When update, Then only out of bounds removed")
    void testUpdateProjectiles_RemovesOnlyOutOfBounds() {
        // Given
        Point inBounds = new Point(100, 100);
        Point outOfBounds = new Point(100, 5);
        GameState.projectiles.add(inBounds);
        GameState.projectiles.add(outOfBounds);

        // When
        gameEngine.update();

        // Then
        assertEquals(1, GameState.projectiles.size());
        assertTrue(GameState.projectiles.contains(inBounds));
        assertFalse(GameState.projectiles.contains(outOfBounds));
    }

    // ==================== Tests de updateEnemies ====================

    @Test
    @DisplayName("Given enemies exist, When 20 ticks elapsed, Then enemies move down")
    void testUpdateEnemies_MovesDown() {
        // Given
        Enemy enemy = new Enemy(100, 50, false);
        GameState.enemies.add(enemy);
        GameState.elapsedTicks = 19;

        // When
        gameEngine.update();

        // Then
        assertEquals(60, enemy.positionY);
    }

    @Test
    @DisplayName("Given enemies exist, When less than 20 ticks, Then enemies don't move")
    void testUpdateEnemies_DoesNotMoveBeforeTick() {
        // Given
        Enemy enemy = new Enemy(100, 50, false);
        GameState.enemies.add(enemy);
        GameState.elapsedTicks = 10;

        // When
        gameEngine.update();

        // Then
        assertEquals(50, enemy.positionY);
    }

    // ==================== Tests de checkCollisions ====================

    @Test
    @DisplayName("Given projectile hits enemy, When collision check, Then enemy removed and score increases")
    void testCheckCollisions_RemovesEnemyAndIncreasesScore() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);
        Point projectile = new Point(110, 110);
        GameState.enemies.add(enemy);
        GameState.projectiles.add(projectile);
        GameState.score = 0;

        // When
        gameEngine.update();

        // Then
        assertFalse(GameState.enemies.contains(enemy));
        assertFalse(GameState.projectiles.contains(projectile));
        assertEquals(10, GameState.score);
    }

    @Test
    @DisplayName("Given projectile hits boss enemy, When collision, Then health decreases and score increases")
    void testCheckCollisions_BossEnemyHealthDecreases() {
        // Given
        Enemy bossEnemy = new Enemy(100, 100, true);
        bossEnemy.health = 3;
        Point projectile = new Point(110, 110);
        GameState.enemies.add(bossEnemy);
        GameState.projectiles.add(projectile);
        GameState.score = 0;

        // When
        gameEngine.update();

        // Then
        assertEquals(2, bossEnemy.health);
        assertTrue(GameState.enemies.contains(bossEnemy));
        assertEquals(10, GameState.score);
    }

    @Test
    @DisplayName("Given projectile kills boss enemy, When collision, Then boss removed and score increases by 50")
    void testCheckCollisions_BossEnemyKilled() {
        // Given
        Enemy bossEnemy = new Enemy(100, 100, true);
        bossEnemy.health = 1;
        Point projectile = new Point(110, 110);
        GameState.enemies.add(bossEnemy);
        GameState.projectiles.add(projectile);
        GameState.score = 0;

        // When
        gameEngine.update();

        // Then
        assertFalse(GameState.enemies.contains(bossEnemy));
        assertEquals(50, GameState.score);
    }

    @Test
    @DisplayName("Given no collision, When check, Then nothing changes")
    void testCheckCollisions_NoCollision() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);
        Point projectile = new Point(500, 500);
        GameState.enemies.add(enemy);
        GameState.projectiles.add(projectile);
        int initialEnemiesCount = GameState.enemies.size();
        int initialProjectilesCount = GameState.projectiles.size();

        // When
        gameEngine.update();

        // Then
        assertEquals(initialEnemiesCount, GameState.enemies.size());
        assertTrue(GameState.enemies.contains(enemy));
    }

    // ==================== Tests de checkGameOver ====================

    @Test
    @DisplayName("Given enemy reaches ship position, When check, Then game over triggered")
    void testCheckGameOver_EnemyReachesShip() {
        // Given
        Enemy enemy = new Enemy(100, GameState.SHIP_POSITION_Y - GameState.ENEMY_HEIGHT, false);
        GameState.enemies.add(enemy);

        // When
        gameEngine.update();

        // Then
        assertTrue(GameState.isGameOver);
        assertTrue(testGameTimer.stopGameCalled);
    }

    @Test
    @DisplayName("Given enemy not at ship, When check, Then game continues")
    void testCheckGameOver_EnemyNotAtShip() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);
        GameState.enemies.add(enemy);

        // When
        gameEngine.update();

        // Then
        assertFalse(GameState.isGameOver);
        assertFalse(testGameTimer.stopGameCalled);
    }

    @Test
    @DisplayName("Given multiple enemies with one reaching ship, When check, Then game over triggered")
    void testCheckGameOver_MultipleEnemiesOneReaches() {
        // Given
        Enemy enemy1 = new Enemy(100, 100, false);
        Enemy enemy2 = new Enemy(200, GameState.SHIP_POSITION_Y - GameState.ENEMY_HEIGHT, false);
        GameState.enemies.add(enemy1);
        GameState.enemies.add(enemy2);

        // When
        gameEngine.update();

        // Then
        assertTrue(GameState.isGameOver);
        assertTrue(testGameTimer.stopGameCalled);
    }

    // ==================== Tests de checkGameWin ====================

    @Test
    @DisplayName("Given all enemies defeated and no boss, When check, Then boss spawns")
    void testCheckGameWin_SpawnsBoss() {
        // Given
        GameState.enemies.clear();
        GameState.isBossSpawned = false;
        GameState.boss = null;

        // When
        gameEngine.update();

        // Then
        assertTrue(GameState.isBossSpawned);
        assertNotNull(GameState.boss);
        assertEquals(20, GameState.boss.health);
    }

    @Test
    @DisplayName("Given boss alive, When check, Then game continues")
    void testCheckGameWin_BossAlive() {
        // Given
        GameState.enemies.clear();
        GameState.boss = new Boss(100, 100, 20);

        // When
        gameEngine.update();

        // Then
        assertFalse(GameState.isGameWin);
        assertFalse(testGameTimer.stopGameCalled);
    }

    @Test
    @DisplayName("Given enemies still present, When check, Then no boss spawn")
    void testCheckGameWin_EnemiesStillPresent() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);
        GameState.enemies.add(enemy);
        GameState.isBossSpawned = false;
        GameState.boss = null;

        // When
        gameEngine.update();

        // Then
        assertFalse(GameState.isBossSpawned);
        assertNull(GameState.boss);
    }

    // ==================== Tests de updateBoss ====================

    @Test
    @DisplayName("Given boss alive, When update multiple times, Then boss shoots projectiles")
    void testUpdateBoss_BossShoots() {
        // Given
        Boss boss = new Boss(300, 50, 20);
        GameState.boss = boss;
        GameState.bossProjectiles.clear();

        // When - On fait plusieurs updates pour atteindre le cooldown de tir
        for (int i = 0; i < 61; i++) {
            gameEngine.update();
        }

        // Then
        assertFalse(GameState.bossProjectiles.isEmpty());
    }

    @Test
    @DisplayName("Given boss dead, When update, Then boss doesn't shoot")
    void testUpdateBoss_DeadBossDoesNotShoot() {
        // Given
        Boss boss = new Boss(300, 50, 0);
        GameState.boss = boss;
        GameState.bossProjectiles.clear();

        // When
        for (int i = 0; i < 61; i++) {
            gameEngine.update();
        }

        // Then
        assertTrue(GameState.bossProjectiles.isEmpty());
    }

    @Test
    @DisplayName("Given no boss, When update, Then no error occurs")
    void testUpdateBoss_NoBoss() {
        // Given
        GameState.boss = null;

        // When & Then - Ne devrait pas lancer d'exception
        assertDoesNotThrow(() -> gameEngine.update());
    }

    // ==================== Tests de updateBossProjectiles ====================

    @Test
    @DisplayName("Given boss projectile, When update, Then projectile moves down")
    void testUpdateBossProjectiles_MovesDown() {
        // Given
        Point projectile = new Point(100, 200);
        GameState.bossProjectiles.add(projectile);

        // When
        gameEngine.update();

        // Then
        assertEquals(208, projectile.y);
    }

    @Test
    @DisplayName("Given boss projectile out of bounds, When update, Then projectile removed")
    void testUpdateBossProjectiles_RemovesOutOfBounds() {
        // Given
        Point projectile = new Point(100, 495);
        GameState.bossProjectiles.add(projectile);

        // When
        gameEngine.update();

        // Then
        assertFalse(GameState.bossProjectiles.contains(projectile));
        assertTrue(GameState.bossProjectiles.isEmpty());
    }

    @Test
    @DisplayName("Given multiple boss projectiles, When update, Then only out of bounds removed")
    void testUpdateBossProjectiles_RemovesOnlyOutOfBounds() {
        // Given
        Point inBounds = new Point(100, 200);
        Point outOfBounds = new Point(100, 495);
        GameState.bossProjectiles.add(inBounds);
        GameState.bossProjectiles.add(outOfBounds);

        // When
        gameEngine.update();

        // Then
        assertEquals(1, GameState.bossProjectiles.size());
        assertTrue(GameState.bossProjectiles.contains(inBounds));
        assertFalse(GameState.bossProjectiles.contains(outOfBounds));
    }

    // ==================== Tests de checkBossCollisions ====================

    @Test
    @DisplayName("Given projectile hits boss, When collision, Then boss takes damage and score increases")
    void testCheckBossCollisions_BossTakesDamage() {
        // Given
        Boss boss = new Boss(100, 100, 20);
        GameState.boss = boss;
        Point projectile = new Point(110, 110);
        GameState.projectiles.add(projectile);
        int initialHealth = boss.health;
        GameState.score = 0;

        // When
        gameEngine.update();

        // Then
        assertEquals(initialHealth - 1, boss.health);
        assertFalse(GameState.projectiles.contains(projectile));
        assertEquals(20, GameState.score);
    }

    @Test
    @DisplayName("Given projectile kills boss, When collision, Then score increases by 500")
    void testCheckBossCollisions_BossKilled() {
        // Given
        Boss boss = new Boss(100, 100, 1);
        GameState.boss = boss;
        Point projectile = new Point(110, 110);
        GameState.projectiles.add(projectile);
        GameState.score = 0;

        // When
        gameEngine.update();

        // Then
        assertFalse(boss.isAlive());
        assertEquals(500, GameState.score);
    }

    @Test
    @DisplayName("Given no boss, When collision check, Then no error occurs")
    void testCheckBossCollisions_NoBoss() {
        // Given
        GameState.boss = null;
        Point projectile = new Point(110, 110);
        GameState.projectiles.add(projectile);

        // When & Then
        assertDoesNotThrow(() -> gameEngine.update());
        assertTrue(GameState.projectiles.contains(projectile)); // Projectile non retiré
    }

    @Test
    @DisplayName("Given dead boss, When collision check, Then no damage taken")
    void testCheckBossCollisions_DeadBoss() {
        // Given
        Boss boss = new Boss(100, 100, 0);
        GameState.boss = boss;
        Point projectile = new Point(110, 110);
        GameState.projectiles.add(projectile);

        // When
        gameEngine.update();

        // Then
        assertTrue(GameState.projectiles.contains(projectile)); // Projectile non retiré
    }

    // ==================== Tests de checkBossGameOver ====================

    @Test
    @DisplayName("Given boss reaches ship, When check, Then game over triggered")
    void testCheckBossGameOver_BossReachesShip() {
        // Given
        Boss boss = new Boss(100, GameState.SHIP_POSITION_Y - GameState.BOSS_HEIGHT, 20);
        GameState.boss = boss;

        // When
        gameEngine.update();

        // Then
        assertTrue(GameState.isGameOver);
        assertTrue(testGameTimer.stopGameCalled);
    }

    @Test
    @DisplayName("Given boss projectile hits ship, When check, Then game over triggered")
    void testCheckBossGameOver_ProjectileHitsShip() {
        // Given
        Boss boss = new Boss(100, 50, 20);
        GameState.boss = boss;
        Point projectile = new Point(GameState.SHIP_POSITION_X + 5, GameState.SHIP_POSITION_Y + 5);
        GameState.bossProjectiles.add(projectile);

        // When
        gameEngine.update();

        // Then
        assertTrue(GameState.isGameOver);
        assertTrue(testGameTimer.stopGameCalled);
    }

    @Test
    @DisplayName("Given no boss, When check game over, Then no error occurs")
    void testCheckBossGameOver_NoBoss() {
        // Given
        GameState.boss = null;

        // When & Then
        assertDoesNotThrow(() -> gameEngine.update());
        assertFalse(GameState.isGameOver);
    }

    // ==================== Tests d'intégration ====================

    @Test
    @DisplayName("Given normal game flow, When updates occur, Then elapsed ticks increase")
    void testIntegration_ElapsedTicksIncrease() {
        // Given
        GameState.elapsedTicks = 0;

        // When
        gameEngine.update();
        gameEngine.update();
        gameEngine.update();

        // Then
        assertEquals(3, GameState.elapsedTicks);
    }

    @Test
    @DisplayName("Given complete game scenario, When all enemies destroyed then boss defeated, Then game win")
    void testIntegration_CompleteGameWin() {
        // Given - Pas d'ennemis, pas de boss
        GameState.enemies.clear();
        GameState.boss = null;
        GameState.isBossSpawned = false;

        // When - Premier update pour spawner le boss
        gameEngine.update();

        // Then - Boss spawné
        assertNotNull(GameState.boss);
        assertTrue(GameState.isBossSpawned);

        // When - On tue le boss
        Boss boss = GameState.boss;
        while (boss.health > 0) {
            Point projectile = new Point(boss.positionX + 10, boss.positionY + 10);
            GameState.projectiles.add(projectile);
            gameEngine.update();
        }

        // Then - Victoire
        assertTrue(GameState.isGameWin);
        assertTrue(testGameTimer.stopGameCalled);
    }
}