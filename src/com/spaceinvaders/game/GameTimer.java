package src.com.spaceinvaders.game;

import src.com.spaceinvaders.utils.functions.CollisionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameTimer extends JPanel {
    private final javax.swing.Timer gameLoopTimer;

    public GameTimer() {
        gameLoopTimer = new javax.swing.Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                GameState.elapsedTicks++;

                ArrayList<Point> projectilesToRemove = new ArrayList<>();
                ArrayList<Point> enemiesToRemove = new ArrayList<>();

                for (Point projectile : GameState.projectiles) {
                    projectile.y -= 10;
                    if (projectile.y < 0) {
                        projectilesToRemove.add(projectile);
                    }
                }

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

                if (GameState.elapsedTicks % 20 == 0) {
                    for (Point enemy : GameState.enemies) {
                        enemy.y += 10;
                    }
                }

                for (Point enemy : GameState.enemies) {
                    if (enemy.y >= GameState.SHIP_POSITION_Y) {
                        gameLoopTimer.stop();
                        break;
                    }
                }

                repaint();
            }
        });

        gameLoopTimer.start();
    }
}