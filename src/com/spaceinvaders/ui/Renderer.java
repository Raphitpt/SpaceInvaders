package src.com.spaceinvaders.ui;

import src.com.spaceinvaders.game.Enemy;
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
        for (Enemy enemy : GameState.enemies) {
            if (enemy.isBoss) {
                graphics.setColor(GameState.BOSS_COLOR);
                graphics.fillRect(enemy.x, enemy.y, GameState.BOSS_WIDTH, GameState.BOSS_HEIGHT);
            } else {
                graphics.setColor(GameState.ENEMY_COLOR);
                graphics.fillRect(enemy.x, enemy.y, GameState.ENEMY_WIDTH, GameState.ENEMY_HEIGHT);
            }
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
