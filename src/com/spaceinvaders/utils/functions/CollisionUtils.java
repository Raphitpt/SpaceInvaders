package src.com.spaceinvaders.utils.functions;

import src.com.spaceinvaders.game.Boss;
import src.com.spaceinvaders.game.Enemy;
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
    public static boolean isProjectileCollidingWithEnemy(Point projectile, Enemy enemy) {
        return projectile.x >= enemy.positionX && projectile.x <= enemy.positionX + GameState.ENEMY_WIDTH &&
                projectile.y >= enemy.positionY && projectile.y <= enemy.positionY + GameState.ENEMY_HEIGHT;
    }

    /**
     * Checks if a projectile collides with the boss.
     *
     * @param projectile Position of the projectile
     * @param boss The boss entity
     * @return true if collision detected, false otherwise
     */
    public static boolean isProjectileCollidingWithBoss(Point projectile, Boss boss) {
        return projectile.x >= boss.positionX && projectile.x <= boss.positionX + GameState.BOSS_WIDTH &&
                projectile.y >= boss.positionY && projectile.y <= boss.positionY + GameState.BOSS_HEIGHT;
    }

    /**
     * Checks if a boss projectile hits the player's ship.
     *
     * @param projectile Position of the boss projectile
     * @return true if collision detected, false otherwise
     */
    public static boolean isBossProjectileHittingShip(Point projectile) {
        return projectile.x >= GameState.SHIP_POSITION_X &&
               projectile.x <= GameState.SHIP_POSITION_X + GameState.SHIP_WIDTH &&
               projectile.y >= GameState.SHIP_POSITION_Y &&
               projectile.y <= GameState.SHIP_POSITION_Y + GameState.SHIP_HEIGHT;
    }
}