package src.com.spaceinvaders.game;

import src.com.spaceinvaders.config.GameConfig;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {
    public static final int SHIP_WIDTH = 30;
    public static final int SHIP_HEIGHT = 15;
    public static int SHIP_POSITION_X = GameConfig.getShipStartX(SHIP_WIDTH);
    public static int SHIP_POSITION_Y = 450;

    public static Color ENEMY_COLOR = Color.WHITE;
    public static int ENEMY_WIDTH = 30;
    public static int ENEMY_HEIGHT = 20;

    public static Color BOSS_COLOR = Color.RED;
    public static int BOSS_WIDTH = 30;
    public static int BOSS_HEIGHT = 20;

    public static void initEnemies() {
        Random random = new Random();
        int rows = 5;
        int cols = 10;
        int spacing = 10;

        // Calcul de la largeur totale de la grille
        int gridWidth = cols * BOSS_WIDTH + (cols - 1) * spacing;
        int startX = GameConfig.getEnemiesGridStartX(gridWidth);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = startX + col * (BOSS_WIDTH + spacing);
                int y = 50 + row * (BOSS_HEIGHT + spacing);

                boolean isBoss = random.nextDouble() < 0.2; // 20% de chance d'être boss
                enemies.add(new Enemy(x, y, isBoss));
            }
        }
    }

    public static final ArrayList<Point> projectiles = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();

    public static int score = 0;
    public static int elapsedTicks = 0;
    public static boolean isGameOver = false;
    public static boolean isGameWin = false;

    public static void moveShip(int deltaX) {
        SHIP_POSITION_X += deltaX;
    }
}
