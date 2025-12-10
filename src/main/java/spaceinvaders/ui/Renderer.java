package spaceinvaders.ui;

import spaceinvaders.config.GameConfig;
import spaceinvaders.game.Enemy;
import spaceinvaders.game.GameState;
import spaceinvaders.game.GameTimer;

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
                graphics.setColor(GameState.ENEMY_SHIELD_COLOR);
                graphics.fillRect(enemy.positionX, enemy.positionY, GameState.ENEMY_SHIELD_WIDTH, GameState.ENEMY_SHIELD_HEIGHT);
            } else {
                graphics.setColor(GameState.ENEMY_COLOR);
                graphics.fillRect(enemy.positionX, enemy.positionY, GameState.ENEMY_WIDTH, GameState.ENEMY_HEIGHT);
            }
        }
    }

    public void drawBoss(Graphics graphics) {
        if (GameState.boss != null && GameState.boss.isAlive()) {
            graphics.setColor(Color.ORANGE);
            graphics.fillRect(GameState.boss.positionX, GameState.boss.positionY,
                            GameState.BOSS_WIDTH, GameState.BOSS_HEIGHT);

            graphics.setColor(Color.YELLOW);
            int barWidth = GameState.BOSS_WIDTH;
            int barHeight = 5;
            int barX = GameState.boss.positionX;
            int barY = GameState.boss.positionY - 10;

            graphics.drawRect(barX, barY, barWidth, barHeight);

            int healthWidth = (int) ((double) GameState.boss.health / GameState.boss.maxHealth * barWidth);
            graphics.fillRect(barX, barY, healthWidth, barHeight);
        }
    }

    public void drawBossProjectiles(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        for (Point point : GameState.bossProjectiles) {
            graphics.fillOval(point.x - 3, point.y - 3, 6, 6);
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
        graphics.drawString("Timer: "+ GameTimer.getElapsedSeconds(), 20, 60);
    }

    public void drawGameOver(Graphics graphics){
        if (GameState.isGameOver) {
            drawCenteredText(graphics, "GAME OVER", Color.RED);
        }
    }

    public void drawWin(Graphics graphics){
        if (GameState.isGameWin) {
            drawCenteredText(graphics, "YOU WIN IN ONLY "+GameTimer.getElapsedSeconds() + " SECONDS !", Color.GREEN);
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
