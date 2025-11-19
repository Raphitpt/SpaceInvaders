package src.com.spaceinvaders.game;

import src.com.spaceinvaders.utils.functions.CollisionUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private final GameTimer gameTimer;

    public GameEngine(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
        GameState.initEnemies();
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
            for (Enemy enemy : GameState.enemies) {
                enemy.y += 10;
            }
        }
    }

    private void checkCollisions() {
        List<Point> projectilesToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();

        for (Point projectile : GameState.projectiles) {
            for (Enemy enemy : GameState.enemies) {
                if (CollisionUtils.isProjectileCollidingWithEnemy(projectile, enemy)) {
                    projectilesToRemove.add(projectile);

                    if (enemy.isBoss) {
                        enemy.health--;
                        if (enemy.health <= 0) {
                            enemiesToRemove.add(enemy);
                            GameState.score += 50;
                        } else {
                            GameState.score += 10;
                        }
                    } else {
                        enemiesToRemove.add(enemy);
                        GameState.score += 10;
                    }
                }
            }
        }

        GameState.projectiles.removeAll(projectilesToRemove);
        GameState.enemies.removeAll(enemiesToRemove);
    }

    private void checkGameOver() {
        for (Enemy enemy : GameState.enemies) {
            if (enemy.y >= GameState.SHIP_POSITION_Y) {
                gameTimer.stopGame();
                break;
            }
        }
    }
}
