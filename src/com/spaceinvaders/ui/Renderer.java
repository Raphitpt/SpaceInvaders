package src.com.spaceinvaders.ui;

import src.com.spaceinvaders.game.GameState;

import java.awt.*;

public class Renderer {
    public void drawShip(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.fillRect(GameState.SHIP_POSITION_X, GameState.SHIP_POSITION_Y, 30, 15);
    }

    public void drawBullet(Graphics graphics) {
        graphics.setColor(Color.RED);
        for (Point point : GameState.projectiles) {
            graphics.fillRect(point.x, point.y, 5, 10);
        }
    }

    public void drawEnemies(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        for (Point point : GameState.enemies) {
            graphics.fillRect(point.x, point.y, 30, 20);
        }
    }

    public void drawScore(Graphics graphics){
        graphics.setColor(Color.YELLOW);
        graphics.drawString("Score: "+GameState.score, 10, 20);
    }

    public void drawGameOver(Graphics graphics){
        graphics.setColor(Color.RED);
        graphics.drawString("GAME OVER", 200, 250);
    }
}
