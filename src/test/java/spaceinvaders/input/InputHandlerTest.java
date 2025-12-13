package spaceinvaders.input;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import spaceinvaders.config.GameConfig;
import spaceinvaders.game.GameState;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
public class InputHandlerTest {

    private InputHandler inputHandler;
    private JPanel panel;
    private int initialShipPositionX;

    @BeforeEach
    void setUp() {
        panel = new JPanel();
        inputHandler = new InputHandler(panel);
        inputHandler.setupKeyboardListener();
        initialShipPositionX = GameState.SHIP_POSITION_X;
    }

    @AfterEach
    void tearDown() {
        // Restaurer la position initiale après chaque test
        GameState.SHIP_POSITION_X = initialShipPositionX;

        // Nettoyer les projectiles pour éviter les effets de bord entre tests
        GameState.projectiles.clear();
    }

    @Test
    @DisplayName("Should move ship left when position is greater than 0")
    void shouldMoveShipLeftWhenPositionIsGreaterThanZero() {
        // Given
        GameState.SHIP_POSITION_X = 100;
        int expectedPosition = 100 - GameConfig.getShipSpeed(); // shipSpeed = 10

        // When
        java.awt.event.KeyEvent event = new java.awt.event.KeyEvent(
                panel,
                java.awt.event.KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                java.awt.event.KeyEvent.VK_LEFT,
                java.awt.event.KeyEvent.CHAR_UNDEFINED
        );

        for (java.awt.event.KeyListener listener : panel.getKeyListeners()) {
            listener.keyPressed(event);
        }

        // Then
        assertEquals(expectedPosition, GameState.SHIP_POSITION_X);
    }

    @Test
    @DisplayName("Should move ship right when position is greater than 0")
    void shouldMoveShipRightWhenPositionIsGreaterThanZero() {
        // Given
        GameState.SHIP_POSITION_X = 100;
        int expectedPosition = 100 + GameConfig.getShipSpeed(); // shipSpeed = 10

        // When
        java.awt.event.KeyEvent event = new java.awt.event.KeyEvent(
                panel,
                java.awt.event.KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_RIGHT,
                java.awt.event.KeyEvent.CHAR_UNDEFINED
        );

        for (java.awt.event.KeyListener listener : panel.getKeyListeners()) {
            listener.keyPressed(event);
        }

        // Then
        assertEquals(expectedPosition, GameState.SHIP_POSITION_X);
    }

    @Test
    @DisplayName("Should add projectile when space key is pressed")
    void shouldAddProjectileWhenSpaceKeyIsPressed() {
        // Given
        GameState.SHIP_POSITION_X = 100;
        GameState.SHIP_POSITION_Y = 500;
        int initialProjectileCount = GameState.projectiles.size();

        int expectedBulletX = 100 + 15; // 115
        int expectedBulletY = 500 - 10; // 490

        // When
        java.awt.event.KeyEvent event = new java.awt.event.KeyEvent(
                panel,
                java.awt.event.KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                java.awt.event.KeyEvent.VK_SPACE,
                ' '
        );

        for (java.awt.event.KeyListener listener : panel.getKeyListeners()) {
            listener.keyPressed(event);
        }

        // Then
        assertEquals(initialProjectileCount + 1, GameState.projectiles.size());

        Point addedProjectile = GameState.projectiles.get(GameState.projectiles.size() - 1);
        assertEquals(expectedBulletX, addedProjectile.x);
        assertEquals(expectedBulletY, addedProjectile.y);
    }

    @Test
    @DisplayName("Should calculate correct bullet position relative to ship")
    void shouldCalculateCorrectBulletPositionRelativeToShip() {
        // Given
        GameState.SHIP_POSITION_X = 50;
        GameState.SHIP_POSITION_Y = 550;

        // When
        java.awt.event.KeyEvent event = new java.awt.event.KeyEvent(
                panel,
                java.awt.event.KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(),
                0,
                java.awt.event.KeyEvent.VK_SPACE,
                ' '
        );

        for (java.awt.event.KeyListener listener : panel.getKeyListeners()) {
            listener.keyPressed(event);
        }

        // Then
        Point lastProjectile = GameState.projectiles.get(GameState.projectiles.size() - 1);
        assertEquals(65, lastProjectile.x); // 50 + 15
        assertEquals(540, lastProjectile.y); // 550 - 10
    }


}
