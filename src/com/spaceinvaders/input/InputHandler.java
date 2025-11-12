package src.com.spaceinvaders.input;

import src.com.spaceinvaders.config.GameConfig;
import src.com.spaceinvaders.game.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InputHandler extends JPanel {

    public void setupKeyboardListener() {
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                InputHandler.this.handleKeyPress(event);
            }
        });
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                movePlayerLeft();
                break;
            case KeyEvent.VK_RIGHT:
                movePlayerRight();
                break;
            case KeyEvent.VK_SPACE:
                firePlayerBullet();
                break;
        }
        repaint();
    }

    private void movePlayerLeft() {
        if (GameState.SHIP_POSITION_X > 0) {
            GameState.SHIP_POSITION_X -= GameConfig.getShipSpeed();
        }
    }

    private void movePlayerRight() {
        if (GameState.SHIP_POSITION_X < 480) {
            GameState.SHIP_POSITION_X += GameConfig.getShipSpeed();
        }
    }

    private void firePlayerBullet() {
        int bulletX = GameState.SHIP_POSITION_X + 15;
        int bulletY = GameState.SHIP_POSITION_Y - 10;
        GameState.shotsCount.add(new Point(bulletX, bulletY));
    }
}
