package spaceinvaders.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Enemy Tests")
class EnemyTest {

    // ==================== Tests du constructeur - Ennemi normal ====================

    @Test
    @DisplayName("Constructeur - Crée un ennemi normal avec les bonnes propriétés")
    void testConstructor_CreatesNormalEnemyWithCorrectProperties() {
        // Given
        int expectedX = 100;
        int expectedY = 50;
        boolean isBoss = false;

        // When
        Enemy enemy = new Enemy(expectedX, expectedY, isBoss);

        // Then
        assertEquals(expectedX, enemy.positionX, "La position X devrait être initialisée correctement");
        assertEquals(expectedY, enemy.positionY, "La position Y devrait être initialisée correctement");
        assertFalse(enemy.isBoss, "L'ennemi ne devrait pas être un boss");
        assertEquals(1, enemy.health, "Un ennemi normal devrait avoir 1 point de vie");
    }

    @Test
    @DisplayName("Constructeur - Ennemi normal a 1 point de vie")
    void testConstructor_NormalEnemyHasOneHealth() {
        // Given & When
        Enemy enemy = new Enemy(0, 0, false);

        // Then
        assertEquals(1, enemy.health, "Un ennemi normal devrait avoir exactement 1 point de vie");
    }

    // ==================== Tests du constructeur - Ennemi boss ====================

    @Test
    @DisplayName("Constructeur - Crée un ennemi boss avec les bonnes propriétés")
    void testConstructor_CreatesBossEnemyWithCorrectProperties() {
        // Given
        int expectedX = 200;
        int expectedY = 100;
        boolean isBoss = true;

        // When
        Enemy enemy = new Enemy(expectedX, expectedY, isBoss);

        // Then
        assertEquals(expectedX, enemy.positionX, "La position X devrait être initialisée correctement");
        assertEquals(expectedY, enemy.positionY, "La position Y devrait être initialisée correctement");
        assertTrue(enemy.isBoss, "L'ennemi devrait être un boss");
        assertEquals(2, enemy.health, "Un ennemi boss devrait avoir 2 points de vie");
    }

    @Test
    @DisplayName("Constructeur - Ennemi boss a 2 points de vie")
    void testConstructor_BossEnemyHasTwoHealth() {
        // Given & When
        Enemy enemy = new Enemy(0, 0, true);

        // Then
        assertEquals(2, enemy.health, "Un ennemi boss devrait avoir exactement 2 points de vie");
    }

    // ==================== Tests paramétrés ====================

    @ParameterizedTest
    @CsvSource({
            "0, 0, false, 1",
            "0, 0, true, 2",
            "100, 50, false, 1",
            "100, 50, true, 2",
            "500, 300, false, 1",
            "500, 300, true, 2",
            "-10, -10, false, 1",
            "-10, -10, true, 2"
    })
    @DisplayName("Constructeur - Initialise correctement avec différentes valeurs")
    void testConstructor_InitializesCorrectlyWithVariousValues(
            int x, int y, boolean isBoss, int expectedHealth) {
        // Given & When
        Enemy enemy = new Enemy(x, y, isBoss);

        // Then
        assertEquals(x, enemy.positionX, "La position X devrait correspondre");
        assertEquals(y, enemy.positionY, "La position Y devrait correspondre");
        assertEquals(isBoss, enemy.isBoss, "Le statut boss devrait correspondre");
        assertEquals(expectedHealth, enemy.health, "La santé devrait correspondre au type d'ennemi");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Constructeur - isBoss détermine correctement la santé")
    void testConstructor_IsBossDeterminesHealth(boolean isBoss) {
        // Given
        int expectedHealth = isBoss ? 2 : 1;

        // When
        Enemy enemy = new Enemy(100, 100, isBoss);

        // Then
        assertEquals(expectedHealth, enemy.health,
                "La santé devrait être " + expectedHealth + " pour isBoss=" + isBoss);
        assertEquals(isBoss, enemy.isBoss, "Le statut boss devrait correspondre");
    }

    // ==================== Tests des positions ====================

    @Test
    @DisplayName("Constructeur - Accepte des positions positives")
    void testConstructor_AcceptsPositivePositions() {
        // Given & When
        Enemy enemy = new Enemy(150, 200, false);

        // Then
        assertEquals(150, enemy.positionX, "Devrait accepter une position X positive");
        assertEquals(200, enemy.positionY, "Devrait accepter une position Y positive");
    }

    @Test
    @DisplayName("Constructeur - Accepte des positions nulles")
    void testConstructor_AcceptsZeroPositions() {
        // Given & When
        Enemy enemy = new Enemy(0, 0, false);

        // Then
        assertEquals(0, enemy.positionX, "Devrait accepter une position X nulle");
        assertEquals(0, enemy.positionY, "Devrait accepter une position Y nulle");
    }

    @Test
    @DisplayName("Constructeur - Accepte des positions négatives")
    void testConstructor_AcceptsNegativePositions() {
        // Given & When
        Enemy enemy = new Enemy(-50, -100, false);

        // Then
        assertEquals(-50, enemy.positionX, "Devrait accepter une position X négative");
        assertEquals(-100, enemy.positionY, "Devrait accepter une position Y négative");
    }

    @Test
    @DisplayName("Constructeur - Accepte de grandes valeurs de position")
    void testConstructor_AcceptsLargePositions() {
        // Given & When
        Enemy enemy = new Enemy(10000, 20000, false);

        // Then
        assertEquals(10000, enemy.positionX, "Devrait accepter une grande position X");
        assertEquals(20000, enemy.positionY, "Devrait accepter une grande position Y");
    }

    // ==================== Tests de différenciation boss/normal ====================

    @Test
    @DisplayName("Différence - Boss et normal ont des santés différentes")
    void testDifference_BossAndNormalHaveDifferentHealth() {
        // Given & When
        Enemy normalEnemy = new Enemy(100, 100, false);
        Enemy bossEnemy = new Enemy(100, 100, true);

        // Then
        assertNotEquals(normalEnemy.health, bossEnemy.health,
                "La santé d'un boss devrait être différente de celle d'un ennemi normal");
        assertEquals(1, normalEnemy.health, "Ennemi normal devrait avoir 1 PV");
        assertEquals(2, bossEnemy.health, "Ennemi boss devrait avoir 2 PV");
    }

    @Test
    @DisplayName("Différence - Deux ennemis à la même position peuvent avoir des statuts différents")
    void testDifference_EnemiesAtSamePositionCanHaveDifferentStatus() {
        // Given
        int sameX = 250;
        int sameY = 150;

        // When
        Enemy normalEnemy = new Enemy(sameX, sameY, false);
        Enemy bossEnemy = new Enemy(sameX, sameY, true);

        // Then
        assertEquals(normalEnemy.positionX, bossEnemy.positionX,
                "Les ennemis devraient avoir la même position X");
        assertEquals(normalEnemy.positionY, bossEnemy.positionY,
                "Les ennemis devraient avoir la même position Y");
        assertNotEquals(normalEnemy.isBoss, bossEnemy.isBoss,
                "Les ennemis devraient avoir des statuts boss différents");
        assertNotEquals(normalEnemy.health, bossEnemy.health,
                "Les ennemis devraient avoir des santés différentes");
    }

    // ==================== Tests d'accès aux champs publics ====================

    @Test
    @DisplayName("Champs publics - Position X peut être modifiée")
    void testPublicFields_PositionXCanBeModified() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);

        // When
        enemy.positionX = 200;

        // Then
        assertEquals(200, enemy.positionX, "La position X devrait pouvoir être modifiée");
    }

    @Test
    @DisplayName("Champs publics - Position Y peut être modifiée")
    void testPublicFields_PositionYCanBeModified() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);

        // When
        enemy.positionY = 250;

        // Then
        assertEquals(250, enemy.positionY, "La position Y devrait pouvoir être modifiée");
    }

    @Test
    @DisplayName("Champs publics - isBoss peut être modifié")
    void testPublicFields_IsBossCanBeModified() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);

        // When
        enemy.isBoss = true;

        // Then
        assertTrue(enemy.isBoss, "Le statut boss devrait pouvoir être modifié");
    }

    @Test
    @DisplayName("Champs publics - health peut être modifiée")
    void testPublicFields_HealthCanBeModified() {
        // Given
        Enemy enemy = new Enemy(100, 100, false);

        // When
        enemy.health = 5;

        // Then
        assertEquals(5, enemy.health, "La santé devrait pouvoir être modifiée");
    }

    // ==================== Tests de cas limites ====================

    @Test
    @DisplayName("Cas limite - Valeurs maximales d'int pour les positions")
    void testEdgeCase_MaxIntPositions() {
        // Given & When
        Enemy enemy = new Enemy(Integer.MAX_VALUE, Integer.MAX_VALUE, false);

        // Then
        assertEquals(Integer.MAX_VALUE, enemy.positionX,
                "Devrait accepter Integer.MAX_VALUE pour X");
        assertEquals(Integer.MAX_VALUE, enemy.positionY,
                "Devrait accepter Integer.MAX_VALUE pour Y");
    }

    @Test
    @DisplayName("Cas limite - Valeurs minimales d'int pour les positions")
    void testEdgeCase_MinIntPositions() {
        // Given & When
        Enemy enemy = new Enemy(Integer.MIN_VALUE, Integer.MIN_VALUE, false);

        // Then
        assertEquals(Integer.MIN_VALUE, enemy.positionX,
                "Devrait accepter Integer.MIN_VALUE pour X");
        assertEquals(Integer.MIN_VALUE, enemy.positionY,
                "Devrait accepter Integer.MIN_VALUE pour Y");
    }

    // ==================== Tests de création multiple ====================

    @Test
    @DisplayName("Création multiple - Plusieurs ennemis normaux sont indépendants")
    void testMultipleCreation_NormalEnemiesAreIndependent() {
        // Given & When
        Enemy enemy1 = new Enemy(100, 100, false);
        Enemy enemy2 = new Enemy(200, 200, false);

        // Modification d'un ennemi
        enemy1.positionX = 150;
        enemy1.health = 0;

        // Then
        assertNotEquals(enemy1.positionX, enemy2.positionX,
                "Les ennemis devraient avoir des positions X indépendantes");
        assertEquals(200, enemy2.positionX, "La position de enemy2 ne devrait pas changer");
        assertEquals(1, enemy2.health, "La santé de enemy2 ne devrait pas changer");
    }

    @Test
    @DisplayName("Création multiple - Ennemis boss et normaux coexistent")
    void testMultipleCreation_BossAndNormalEnemiesCoexist() {
        // Given & When
        Enemy normalEnemy1 = new Enemy(50, 50, false);
        Enemy bossEnemy = new Enemy(100, 100, true);
        Enemy normalEnemy2 = new Enemy(150, 150, false);

        // Then
        assertFalse(normalEnemy1.isBoss, "Le premier ennemi devrait être normal");
        assertTrue(bossEnemy.isBoss, "L'ennemi du milieu devrait être un boss");
        assertFalse(normalEnemy2.isBoss, "Le troisième ennemi devrait être normal");

        assertEquals(1, normalEnemy1.health, "Ennemi normal devrait avoir 1 PV");
        assertEquals(2, bossEnemy.health, "Ennemi boss devrait avoir 2 PV");
        assertEquals(1, normalEnemy2.health, "Ennemi normal devrait avoir 1 PV");
    }
}