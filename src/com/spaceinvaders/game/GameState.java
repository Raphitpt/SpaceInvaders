package src.com.spaceinvaders.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {
    public static int SHIP_POSITION_X = 250;
    public static int SHIP_POSITION_Y = 450;

    public static Color ENEMY_COLOR = Color.WHITE;
    public static int ENEMY_WIDTH = 30;
    public static int ENEMY_HEIGHT = 20;

    public static Color BOSS_COLOR = Color.RED;
    public static int BOSS_WIDTH = 50;
    public static int BOSS_HEIGHT = 50;

    public static void initEnemies() {
        Random random = new Random();
        int rows = 5;
        int cols = 10;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * (ENEMY_WIDTH + 10);
                int y = row * (ENEMY_HEIGHT + 10);

                boolean isBoss = random.nextDouble() < 0.2; // 20% de chance d'être boss
                enemies.add(new Enemy(x, y, isBoss));
            }
        }
    }

    public static final ArrayList<Point> projectiles = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();

    public static int score = 0;
    public static int elapsedTicks = 0;

    public static void moveShip(int deltaX) {
        SHIP_POSITION_X += deltaX;
    }
}
