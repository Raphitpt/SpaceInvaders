package src.com.spaceinvaders.game;

import src.com.spaceinvaders.utils.functions.CollisionUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private final GameTimer gameTimer;

    public GameEngine(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public void update() {
        GameState.elapsedTicks++;

        updateProjectiles();
        updateEnemies();
        checkCollisions();
        checkGameOver();
    }

    private void updateProjectiles() {
        List<Point> toRemove = new ArrayList<>();

        for (Point projectile : GameState.projectiles) {
            projectile.y -= 10;

            if (projectile.y < 0) {
                toRemove.add(projectile);
            }
        }

        GameState.projectiles.removeAll(toRemove);
    }

    private void updateEnemies() {
        if (GameState.elapsedTicks % 20 == 0) {
            for (Point enemy : GameState.enemies) {
                enemy.y += 10;
            }
        }
    }

    private void checkCollisions() {
        List<Point> projectilesToRemove = new ArrayList<>();
        List<Point> enemiesToRemove = new ArrayList<>();

        for (Point projectile : GameState.projectiles) {
            for (Point enemy : GameState.enemies) {
                if (CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy)) {
                    projectilesToRemove.add(projectile);
                    enemiesToRemove.add(enemy);
                    GameState.score += 10;
                }
            }
        }

        GameState.projectiles.removeAll(projectilesToRemove);
        GameState.enemies.removeAll(enemiesToRemove);
    }

    private void checkGameOver() {
        for (Point enemy : GameState.enemies) {
            if (enemy.y >= GameState.SHIP_POSITION_Y) {
                gameTimer.stopGame();
                break;
            }
        }
    }
}
