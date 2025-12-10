package spaceinvaders.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Boss Tests")
class BossTest {

    private Boss boss;
    private static final int INITIAL_X = 300;
    private static final int INITIAL_Y = 100;
    private static final int INITIAL_HEALTH = 20;

    @BeforeEach
    void setUp() {
        boss = new Boss(INITIAL_X, INITIAL_Y, INITIAL_HEALTH);
    }

    // ==================== Tests du constructeur ====================

    @Test
    @DisplayName("Constructeur - Initialise correctement toutes les propriétés")
    void testConstructor_InitializesAllProperties() {
        // Given & When
        Boss newBoss = new Boss(250, 150, 15);

        // Then
        assertEquals(250, newBoss.positionX, "La position X devrait être initialisée correctement");
        assertEquals(150, newBoss.positionY, "La position Y devrait être initialisée correctement");
        assertEquals(15, newBoss.health, "La santé devrait être initialisée correctement");
        assertEquals(15, newBoss.maxHealth, "La santé maximale devrait être égale à la santé initiale");
        assertEquals(1, newBoss.direction, "La direction initiale devrait être 1");
        assertEquals(5, newBoss.zigzagAmplitude, "L'amplitude zigzag devrait être 5");
        assertEquals(0, newBoss.ticksSinceLastShot, "Les ticks depuis le dernier tir devraient être 0");
        assertEquals(30, newBoss.shootingCooldown, "Le cooldown de tir devrait être 30");
    }

    @Test
    @DisplayName("Constructeur - Santé initiale est égale à la santé maximale")
    void testConstructor_HealthEqualsMaxHealth() {
        // Given & When
        Boss newBoss = new Boss(100, 100, 50);

        // Then
        assertEquals(newBoss.health, newBoss.maxHealth, "La santé devrait être égale à maxHealth");
    }

    // ==================== Tests de updatePosition ====================

    @Test
    @DisplayName("updatePosition - Déplace le boss vers la droite initialement")
    void testUpdatePosition_MovesRightInitially() {
        // Given
        int initialX = boss.positionX;

        // When
        boss.updatePosition();

        // Then
        assertEquals(initialX + 5, boss.positionX,
                "Le boss devrait se déplacer de 5 pixels vers la droite");
    }

    @Test
    @DisplayName("updatePosition - Change de direction à la limite gauche")
    void testUpdatePosition_ChangesDirectionAtLeftBoundary() {
        // Given
        boss.positionX = 50;
        boss.direction = -1;

        // When
        boss.updatePosition();

        // Then
        assertEquals(1, boss.direction, "La direction devrait s'inverser à la limite gauche");
    }

    @Test
    @DisplayName("updatePosition - Change de direction à la limite droite")
    void testUpdatePosition_ChangesDirectionAtRightBoundary() {
        // Given
        boss.positionX = 550;
        boss.direction = 1;

        // When
        boss.updatePosition();

        // Then
        assertEquals(-1, boss.direction, "La direction devrait s'inverser à la limite droite");
    }

    @Test
    @DisplayName("updatePosition - Déplace vers la gauche après inversion de direction")
    void testUpdatePosition_MovesLeftAfterDirectionChange() {
        // Given
        boss.positionX = 555;
        boss.direction = 1;
        boss.updatePosition(); // Déclenche l'inversion
        int positionAfterInversion = boss.positionX;

        // When
        boss.updatePosition();

        // Then
        assertEquals(positionAfterInversion - 5, boss.positionX,
                "Le boss devrait se déplacer vers la gauche après inversion");
    }

    @Test
    @DisplayName("updatePosition - La position Y peut augmenter aléatoirement")
    void testUpdatePosition_YPositionCanIncrease() {
        // Given
        int initialY = boss.positionY;
        boolean yIncreased = false;

        // When - Test sur plusieurs itérations pour la nature aléatoire
        for (int i = 0; i < 100; i++) {
            boss.positionY = initialY; // Reset Y
            boss.updatePosition();
            if (boss.positionY > initialY) {
                yIncreased = true;
                break;
            }
        }

        // Then
        assertTrue(yIncreased,
                "La position Y devrait augmenter au moins une fois sur 100 itérations");
    }

    @Test
    @DisplayName("updatePosition - La position Y reste stable parfois")
    void testUpdatePosition_YPositionCanStayStable() {
        // Given
        int initialY = boss.positionY;
        boolean yStayedStable = false;

        // When - Test sur plusieurs itérations
        for (int i = 0; i < 100; i++) {
            boss.positionY = initialY; // Reset Y
            boss.updatePosition();
            if (boss.positionY == initialY) {
                yStayedStable = true;
                break;
            }
        }

        // Then
        assertTrue(yStayedStable,
                "La position Y devrait rester stable au moins une fois sur 100 itérations");
    }

    @Test
    @DisplayName("updatePosition - Ne dépasse pas la limite gauche lors du mouvement")
    void testUpdatePosition_DoesNotExceedLeftBoundary() {
        // Given
        boss.positionX = 52;
        boss.direction = -1;

        // When
        boss.updatePosition();

        // Then
        assertTrue(boss.positionX >= 45,
                "Le boss ne devrait pas dépasser significativement la limite gauche");
    }

    @Test
    @DisplayName("updatePosition - Ne dépasse pas la limite droite lors du mouvement")
    void testUpdatePosition_DoesNotExceedRightBoundary() {
        // Given
        boss.positionX = 548;
        boss.direction = 1;

        // When
        boss.updatePosition();

        // Then
        assertTrue(boss.positionX <= 555,
                "Le boss ne devrait pas dépasser significativement la limite droite");
    }

    // ==================== Tests de canShoot ====================

    @Test
    @DisplayName("canShoot - Retourne false avant que le cooldown soit atteint")
    void testCanShoot_ReturnsFalseBeforeCooldown() {
        // Given
        boss.ticksSinceLastShot = 0;

        // When
        boolean canShoot = boss.canShoot();

        // Then
        assertFalse(canShoot, "Le boss ne devrait pas pouvoir tirer avant le cooldown");
        assertEquals(1, boss.ticksSinceLastShot, "Les ticks devraient être incrémentés");
    }

    @Test
    @DisplayName("canShoot - Retourne true quand le cooldown est atteint")
    void testCanShoot_ReturnsTrueWhenCooldownReached() {
        // Given
        boss.ticksSinceLastShot = 29;

        // When
        boolean canShoot = boss.canShoot();

        // Then
        assertTrue(canShoot, "Le boss devrait pouvoir tirer quand le cooldown est atteint");
        assertEquals(0, boss.ticksSinceLastShot, "Les ticks devraient être réinitialisés à 0");
    }

    @Test
    @DisplayName("canShoot - Réinitialise le compteur après un tir")
    void testCanShoot_ResetsCounterAfterShooting() {
        // Given
        boss.ticksSinceLastShot = 30;

        // When
        boss.canShoot();

        // Then
        assertEquals(0, boss.ticksSinceLastShot,
                "Le compteur devrait être réinitialisé après un tir");
    }

    @Test
    @DisplayName("canShoot - Incrémente le compteur à chaque appel")
    void testCanShoot_IncrementsCounterEachCall() {
        // Given
        boss.ticksSinceLastShot = 10;

        // When
        boss.canShoot();

        // Then
        assertEquals(11, boss.ticksSinceLastShot,
                "Le compteur devrait être incrémenté de 1");
    }

    @Test
    @DisplayName("canShoot - Permet de tirer plusieurs fois après plusieurs cooldowns")
    void testCanShoot_AllowsMultipleShots() {
        // Given
        boss.ticksSinceLastShot = 0;

        // When - Premier cycle
        for (int i = 0; i < 29; i++) {
            assertFalse(boss.canShoot(), "Ne devrait pas pouvoir tirer avant 30 ticks");
        }
        boolean firstShot = boss.canShoot();

        // When - Deuxième cycle
        for (int i = 0; i < 29; i++) {
            assertFalse(boss.canShoot(), "Ne devrait pas pouvoir tirer avant 30 ticks");
        }
        boolean secondShot = boss.canShoot();

        // Then
        assertTrue(firstShot, "Le premier tir devrait être possible");
        assertTrue(secondShot, "Le second tir devrait être possible");
    }

    // ==================== Tests de takeDamage ====================

    @Test
    @DisplayName("takeDamage - Réduit la santé de 1")
    void testTakeDamage_ReducesHealthByOne() {
        // Given
        int initialHealth = boss.health;

        // When
        boss.takeDamage();

        // Then
        assertEquals(initialHealth - 1, boss.health,
                "La santé devrait être réduite de 1");
    }

    @Test
    @DisplayName("takeDamage - Peut réduire la santé à zéro")
    void testTakeDamage_CanReduceHealthToZero() {
        // Given
        boss.health = 1;

        // When
        boss.takeDamage();

        // Then
        assertEquals(0, boss.health, "La santé devrait pouvoir atteindre 0");
    }

    @Test
    @DisplayName("takeDamage - Peut réduire la santé en dessous de zéro")
    void testTakeDamage_CanReduceHealthBelowZero() {
        // Given
        boss.health = 0;

        // When
        boss.takeDamage();

        // Then
        assertEquals(-1, boss.health,
                "La santé peut descendre en dessous de 0 (pas de vérification)");
    }

    @Test
    @DisplayName("takeDamage - Dégâts multiples réduisent correctement la santé")
    void testTakeDamage_MultipleDamagesReduceHealthCorrectly() {
        // Given
        boss.health = 5;

        // When
        boss.takeDamage();
        boss.takeDamage();
        boss.takeDamage();

        // Then
        assertEquals(2, boss.health,
                "La santé devrait être réduite correctement après plusieurs dégâts");
    }

    // ==================== Tests de isAlive ====================

    @Test
    @DisplayName("isAlive - Retourne true avec une santé positive")
    void testIsAlive_ReturnsTrueWithPositiveHealth() {
        // Given
        boss.health = 10;

        // When
        boolean alive = boss.isAlive();

        // Then
        assertTrue(alive, "Le boss devrait être vivant avec une santé positive");
    }

    @Test
    @DisplayName("isAlive - Retourne true avec une santé de 1")
    void testIsAlive_ReturnsTrueWithHealthOne() {
        // Given
        boss.health = 1;

        // When
        boolean alive = boss.isAlive();

        // Then
        assertTrue(alive, "Le boss devrait être vivant avec 1 point de vie");
    }

    @Test
    @DisplayName("isAlive - Retourne false avec une santé de 0")
    void testIsAlive_ReturnsFalseWithZeroHealth() {
        // Given
        boss.health = 0;

        // When
        boolean alive = boss.isAlive();

        // Then
        assertFalse(alive, "Le boss ne devrait pas être vivant avec 0 point de vie");
    }

    @Test
    @DisplayName("isAlive - Retourne false avec une santé négative")
    void testIsAlive_ReturnsFalseWithNegativeHealth() {
        // Given
        boss.health = -5;

        // When
        boolean alive = boss.isAlive();

        // Then
        assertFalse(alive, "Le boss ne devrait pas être vivant avec une santé négative");
    }

    // ==================== Tests d'intégration ====================

    @Test
    @DisplayName("Integration - Boss meurt après avoir reçu tous les dégâts")
    void testIntegration_BossDiesAfterTakingAllDamage() {
        // Given
        Boss testBoss = new Boss(300, 100, 3);

        // When
        assertTrue(testBoss.isAlive(), "Le boss devrait être vivant initialement");

        testBoss.takeDamage();
        assertTrue(testBoss.isAlive(), "Le boss devrait être vivant après 1 dégât");

        testBoss.takeDamage();
        assertTrue(testBoss.isAlive(), "Le boss devrait être vivant après 2 dégâts");

        testBoss.takeDamage();

        // Then
        assertFalse(testBoss.isAlive(), "Le boss devrait être mort après 3 dégâts");
        assertEquals(0, testBoss.health, "La santé devrait être à 0");
    }

    @Test
    @DisplayName("Integration - Cycle complet de tir")
    void testIntegration_CompleteShotCycle() {
        // Given
        boss.ticksSinceLastShot = 0;
        int shotsCount = 0;

        // When - Simule 100 ticks
        for (int i = 0; i < 100; i++) {
            if (boss.canShoot()) {
                shotsCount++;
            }
        }

        // Then
        assertEquals(3, shotsCount,
                "Le boss devrait avoir tiré 3 fois en 100 ticks (tous les 30 ticks)");
    }

    @Test
    @DisplayName("Integration - maxHealth ne change jamais")
    void testIntegration_MaxHealthNeverChanges() {
        // Given
        int originalMaxHealth = boss.maxHealth;

        // When
        boss.takeDamage();
        boss.takeDamage();
        boss.updatePosition();
        boss.canShoot();

        // Then
        assertEquals(originalMaxHealth, boss.maxHealth,
                "maxHealth devrait rester constant");
    }
}