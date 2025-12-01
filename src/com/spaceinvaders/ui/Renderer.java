package src.com.spaceinvaders.ui;

import src.com.spaceinvaders.config.GameConfig;
import src.com.spaceinvaders.game.Enemy;
import src.com.spaceinvaders.game.GameState;

import java.awt.*;

public class Renderer {
    public void drawShip(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.fillRect(GameState.SHIP_POSITION_X, GameState.SHIP_POSITION_Y, GameState.SHIP_WIDTH, GameState.SHIP_HEIGHT);
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
                graphics.fillRect(enemy.positionX, enemy.positionY, GameState.BOSS_WIDTH, GameState.BOSS_HEIGHT);
            } else {
                graphics.setColor(GameState.ENEMY_COLOR);
                graphics.fillRect(enemy.positionX, enemy.positionY, GameState.ENEMY_WIDTH, GameState.ENEMY_HEIGHT);
            }
        }
    }

    public void drawScore(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.drawString("Score: "+GameState.score, 20, 40);
    }

    public void drawTime(Graphics graphics){
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));
        graphics.drawString("Score: "+ GameState.score, 20, 40);
    }

    public void drawGameOver(Graphics graphics){
        if (GameState.isGameOver) {
            drawCenteredText(graphics, "GAME OVER", Color.RED);
        }
    }

    public void drawWin(Graphics graphics){
        if (GameState.isGameWin) {
            drawCenteredText(graphics, "YOU WIN", Color.GREEN);
        }
    }

    private void drawCenteredText(Graphics graphics, String text, Color color) {
        graphics.setColor(color);
        Font font = new Font("Arial", Font.BOLD, 40);
        graphics.setFont(font);

        FontMetrics metrics = graphics.getFontMetrics(font);
        int x = (GameConfig.getWindowWidth() - metrics.stringWidth(text)) / 2;
        int y = GameConfig.getWindowHeight() / 2;

        graphics.drawString(text, x, y);
    }
}
