package spaceinvaders.utils.functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import spaceinvaders.game.Boss;
import spaceinvaders.game.Enemy;
import spaceinvaders.game.GameState;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CollisionUtils Tests")
class CollisionUtilsTest {

    private Enemy enemy;
    private Boss boss;

    @BeforeEach
    void setUp() {
        enemy = new Enemy(100, 50, false);
        boss = new Boss(200, 100, 20);
    }

    // ==================== Tests pour isProjectileCollidingWithEnemy ====================

    @Test
    @DisplayName("Projectile collision avec ennemi - centre de l'ennemi")
    void testProjectileCollidingWithEnemy_Center() {
        // Given
        Point projectile = new Point(
                enemy.positionX + GameState.ENEMY_WIDTH / 2,
                enemy.positionY + GameState.ENEMY_HEIGHT / 2
        );

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);

        // Then
        assertTrue(result, "Le projectile devrait toucher le centre de l'ennemi");
    }

    @Test
    @DisplayName("Projectile collision avec ennemi - coin supérieur gauche")
    void testProjectileCollidingWithEnemy_TopLeft() {
        // Given
        Point projectile = new Point(enemy.positionX, enemy.positionY);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);

        // Then
        assertTrue(result, "Le projectile devrait toucher le coin supérieur gauche de l'ennemi");
    }

    @Test
    @DisplayName("Projectile collision avec ennemi - coin inférieur droit")
    void testProjectileCollidingWithEnemy_BottomRight() {
        // Given
        Point projectile = new Point(
                enemy.positionX + GameState.ENEMY_WIDTH,
                enemy.positionY + GameState.ENEMY_HEIGHT
        );

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);

        // Then
        assertTrue(result, "Le projectile devrait toucher le coin inférieur droit de l'ennemi");
    }

    @Test
    @DisplayName("Pas de collision - projectile à gauche de l'ennemi")
    void testProjectileNotCollidingWithEnemy_Left() {
        // Given
        Point projectile = new Point(enemy.positionX - 1, enemy.positionY);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher l'ennemi (trop à gauche)");
    }

    @Test
    @DisplayName("Pas de collision - projectile à droite de l'ennemi")
    void testProjectileNotCollidingWithEnemy_Right() {
        // Given
        Point projectile = new Point(enemy.positionX + GameState.ENEMY_WIDTH + 1, enemy.positionY);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher l'ennemi (trop à droite)");
    }

    @Test
    @DisplayName("Pas de collision - projectile au-dessus de l'ennemi")
    void testProjectileNotCollidingWithEnemy_Above() {
        // Given
        Point projectile = new Point(enemy.positionX, enemy.positionY - 1);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher l'ennemi (trop haut)");
    }

    @Test
    @DisplayName("Pas de collision - projectile en-dessous de l'ennemi")
    void testProjectileNotCollidingWithEnemy_Below() {
        // Given
        Point projectile = new Point(enemy.positionX, enemy.positionY + GameState.ENEMY_HEIGHT + 1);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher l'ennemi (trop bas)");
    }

    // ==================== Tests pour isProjectileCollidingWithBoss ====================

    @Test
    @DisplayName("Projectile collision avec boss - centre du boss")
    void testProjectileCollidingWithBoss_Center() {
        // Given
        Point projectile = new Point(
                boss.positionX + GameState.BOSS_WIDTH / 2,
                boss.positionY + GameState.BOSS_HEIGHT / 2
        );

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);

        // Then
        assertTrue(result, "Le projectile devrait toucher le centre du boss");
    }

    @Test
    @DisplayName("Projectile collision avec boss - coin supérieur gauche")
    void testProjectileCollidingWithBoss_TopLeft() {
        // Given
        Point projectile = new Point(boss.positionX, boss.positionY);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);

        // Then
        assertTrue(result, "Le projectile devrait toucher le coin supérieur gauche du boss");
    }

    @Test
    @DisplayName("Projectile collision avec boss - coin inférieur droit")
    void testProjectileCollidingWithBoss_BottomRight() {
        // Given
        Point projectile = new Point(
                boss.positionX + GameState.BOSS_WIDTH,
                boss.positionY + GameState.BOSS_HEIGHT
        );

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);

        // Then
        assertTrue(result, "Le projectile devrait toucher le coin inférieur droit du boss");
    }

    @Test
    @DisplayName("Pas de collision - projectile à gauche du boss")
    void testProjectileNotCollidingWithBoss_Left() {
        // Given
        Point projectile = new Point(boss.positionX - 1, boss.positionY);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher le boss (trop à gauche)");
    }

    @Test
    @DisplayName("Pas de collision - projectile à droite du boss")
    void testProjectileNotCollidingWithBoss_Right() {
        // Given
        Point projectile = new Point(boss.positionX + GameState.BOSS_WIDTH + 1, boss.positionY);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher le boss (trop à droite)");
    }

    @Test
    @DisplayName("Pas de collision - projectile au-dessus du boss")
    void testProjectileNotCollidingWithBoss_Above() {
        // Given
        Point projectile = new Point(boss.positionX, boss.positionY - 1);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher le boss (trop haut)");
    }

    @Test
    @DisplayName("Pas de collision - projectile en-dessous du boss")
    void testProjectileNotCollidingWithBoss_Below() {
        // Given
        Point projectile = new Point(boss.positionX, boss.positionY + GameState.BOSS_HEIGHT + 1);

        // When
        boolean result = CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);

        // Then
        assertFalse(result, "Le projectile ne devrait pas toucher le boss (trop bas)");
    }

    // ==================== Tests pour isBossProjectileHittingShip ====================

    @Test
    @DisplayName("Projectile boss touche le vaisseau - centre du vaisseau")
    void testBossProjectileHittingShip_Center() {
        // Given
        Point projectile = new Point(
                GameState.SHIP_POSITION_X + GameState.SHIP_WIDTH / 2,
                GameState.SHIP_POSITION_Y + GameState.SHIP_HEIGHT / 2
        );

        // When
        boolean result = CollisionUtils.isBossProjectileHittingShip(projectile);

        // Then
        assertTrue(result, "Le projectile du boss devrait toucher le centre du vaisseau");
    }

    @Test
    @DisplayName("Projectile boss touche le vaisseau - coin supérieur gauche")
    void testBossProjectileHittingShip_TopLeft() {
        // Given
        Point projectile = new Point(GameState.SHIP_POSITION_X, GameState.SHIP_POSITION_Y);

        // When
        boolean result = CollisionUtils.isBossProjectileHittingShip(projectile);

        // Then
        assertTrue(result, "Le projectile du boss devrait toucher le coin supérieur gauche du vaisseau");
    }

    @Test
    @DisplayName("Projectile boss touche le vaisseau - coin inférieur droit")
    void testBossProjectileHittingShip_BottomRight() {
        // Given
        Point projectile = new Point(
                GameState.SHIP_POSITION_X + GameState.SHIP_WIDTH,
                GameState.SHIP_POSITION_Y + GameState.SHIP_HEIGHT
        );

        // When
        boolean result = CollisionUtils.isBossProjectileHittingShip(projectile);

        // Then
        assertTrue(result, "Le projectile du boss devrait toucher le coin inférieur droit du vaisseau");
    }

    @Test
    @DisplayName("Projectile boss rate le vaisseau - à gauche")
    void testBossProjectileNotHittingShip_Left() {
        // Given
        Point projectile = new Point(GameState.SHIP_POSITION_X - 1, GameState.SHIP_POSITION_Y);

        // When
        boolean result = CollisionUtils.isBossProjectileHittingShip(projectile);

        // Then
        assertFalse(result, "Le projectile du boss ne devrait pas toucher le vaisseau (trop à gauche)");
    }

    @Test
    @DisplayName("Projectile boss rate le vaisseau - à droite")
    void testBossProjectileNotHittingShip_Right() {
        // Given
        Point projectile = new Point(
                GameState.SHIP_POSITION_X + GameState.SHIP_WIDTH + 1,
                GameState.SHIP_POSITION_Y
        );

        // When
        boolean result = CollisionUtils.isBossProjectileHittingShip(projectile);

        // Then
        assertFalse(result, "Le projectile du boss ne devrait pas toucher le vaisseau (trop à droite)");
    }

    @Test
    @DisplayName("Projectile boss rate le vaisseau - au-dessus")
    void testBossProjectileNotHittingShip_Above() {
        // Given
        Point projectile = new Point(GameState.SHIP_POSITION_X, GameState.SHIP_POSITION_Y - 1);

        // When
        boolean result = CollisionUtils.isBossProjectileHittingShip(projectile);

        // Then
        assertFalse(result, "Le projectile du boss ne devrait pas toucher le vaisseau (trop haut)");
    }

    @Test
    @DisplayName("Projectile boss rate le vaisseau - en-dessous")
    void testBossProjectileNotHittingShip_Below() {
        // Given
        Point projectile = new Point(
                GameState.SHIP_POSITION_X,
                GameState.SHIP_POSITION_Y + GameState.SHIP_HEIGHT + 1
        );

        // When
        boolean result = CollisionUtils.isBossProjectileHittingShip(projectile);

        // Then
        assertFalse(result, "Le projectile du boss ne devrait pas toucher le vaisseau (trop bas)");
    }

    // ==================== Tests avec valeurs null ====================

    @Test
    @DisplayName("Test avec projectile null - Enemy")
    void testNullProjectile_Enemy() {
        // Given
        Point projectile = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy);
        }, "Une NullPointerException devrait être levée avec un projectile null");
    }

    @Test
    @DisplayName("Test avec ennemi null")
    void testNullEnemy() {
        // Given
        Point projectile = new Point(100, 100);
        Enemy nullEnemy = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            CollisionUtils.isProjectileCollidingWithEnemy(projectile, nullEnemy);
        }, "Une NullPointerException devrait être levée avec un ennemi null");
    }

    @Test
    @DisplayName("Test avec projectile null - Boss")
    void testNullProjectile_Boss() {
        // Given
        Point projectile = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            CollisionUtils.isProjectileCollidingWithBoss(projectile, boss);
        }, "Une NullPointerException devrait être levée avec un projectile null");
    }

    @Test
    @DisplayName("Test avec boss null")
    void testNullBoss() {
        // Given
        Point projectile = new Point(100, 100);
        Boss nullBoss = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            CollisionUtils.isProjectileCollidingWithBoss(projectile, nullBoss);
        }, "Une NullPointerException devrait être levée avec un boss null");
    }

    @Test
    @DisplayName("Test avec projectile null - Ship")
    void testNullProjectile_Ship() {
        // Given
        Point projectile = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            CollisionUtils.isBossProjectileHittingShip(projectile);
        }, "Une NullPointerException devrait être levée avec un projectile null");
    }
}