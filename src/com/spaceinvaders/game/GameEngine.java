package src.com.spaceinvaders.game;

import src.com.spaceinvaders.input.InputHandler;
import src.com.spaceinvaders.utils.functions.CollisionUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    private final GameTimer gameTimer;

    public GameEngine(GameTimer gameTimer, InputHandler inputHandler) {
        this.gameTimer = gameTimer;
        GameState.initEnemies();
        inputHandler.setupKeyboardListener();
    }

    public void update() {
        GameState.elapsedTicks++;
        updateProjectiles();
        updateBossProjectiles();
        updateEnemies();
        updateBoss();
        checkCollisions();
        checkBossCollisions();
        checkGameOver();
        checkBossGameOver();
        checkGameWin();
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
                enemy.positionY += 10;
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
            if (enemy.positionY + GameState.ENEMY_HEIGHT == GameState.SHIP_POSITION_Y) {
                GameState.isGameOver = true;
                gameTimer.stopGame();
                break;
            }
        }
    }

    private void checkGameWin() {
        if(GameState.enemies.isEmpty() && !GameState.isBossSpawned) {
            spawnBoss();
        }

        if(GameState.boss != null && !GameState.boss.isAlive()) {
            GameState.isGameWin = true;
            gameTimer.stopGame();
        }
    }

    private void spawnBoss() {
        GameState.isBossSpawned = true;
        int bossX = 300;
        int bossY = 50;
        GameState.boss = new Boss(bossX, bossY, 20);
    }

    private void updateBoss() {
        if (GameState.boss != null && GameState.boss.isAlive()) {
            GameState.boss.updatePosition();

            if (GameState.boss.canShoot()) {
                int projectileX = GameState.boss.positionX + GameState.BOSS_WIDTH / 2;
                int projectileY = GameState.boss.positionY + GameState.BOSS_HEIGHT;
                GameState.bossProjectiles.add(new Point(projectileX, projectileY));
            }
        }
    }

    private void updateBossProjectiles() {
        List<Point> toRemove = new ArrayList<>();

        for (Point projectile : GameState.bossProjectiles) {
            projectile.y += 8;

            if (projectile.y > 500) {
                toRemove.add(projectile);
            }
        }

        GameState.bossProjectiles.removeAll(toRemove);
    }

    private void checkBossCollisions() {
        if (GameState.boss == null || !GameState.boss.isAlive()) {
            return;
        }

        List<Point> projectilesToRemove = new ArrayList<>();

        for (Point projectile : GameState.projectiles) {
            if (CollisionUtils.isProjectileCollidingWithBoss(projectile, GameState.boss)) {
                projectilesToRemove.add(projectile);
                GameState.boss.takeDamage();
                GameState.score += GameState.boss.isAlive() ? 20 : 500;
            }
        }

        GameState.projectiles.removeAll(projectilesToRemove);
    }

    private void checkBossGameOver() {
        if (GameState.boss == null || !GameState.boss.isAlive()) {
            return;
        }

        if (GameState.boss.positionY + GameState.BOSS_HEIGHT >= GameState.SHIP_POSITION_Y) {
            GameState.isGameOver = true;
            gameTimer.stopGame();
        }

        for (Point projectile : GameState.bossProjectiles) {
            if (CollisionUtils.isBossProjectileHittingShip(projectile)) {
                GameState.isGameOver = true;
                gameTimer.stopGame();
                break;
            }
        }
    }
}
