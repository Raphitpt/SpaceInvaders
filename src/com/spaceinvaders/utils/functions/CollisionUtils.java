package src.com.spaceinvaders.utils.functions;

import src.com.spaceinvaders.game.GameState;

import java.awt.Point;

public class CollisionUtils {

    /**
     * Checks if a projectile collides with an enemy.
     *
     * @param projectile Position of the projectile
     * @param enemy Position of the enemy
     * @return true if collision detected, false otherwise
     */
    public static boolean isProjectileCollidingWithEnemy(Point projectile, Point enemy) {
        return projectile.x >= enemy.x && projectile.x <= enemy.x + GameState.ENEMY_WIDTH &&
                projectile.y >= enemy.y && projectile.y <= enemy.y + GameState.ENEMY_HEIGHT;
    }
}