package src.com.spaceinvaders.game;

import java.awt.*;
import java.util.ArrayList;

public class GameState {
    public static int SHIP_POSITION_X = 250;
    public static int SHIP_POSITION_Y = 450;

    public static int ENEMY_WIDTH = 30;
    public static int ENEMY_HEIGHT = 20;

    public static final ArrayList<Point> projectiles = new ArrayList<>();
    public static final ArrayList<Point> enemies = new ArrayList<>();

    public static int score = 0;
    public static int elapsedTicks = 0;

    public static void moveShip(int deltaX) {
        SHIP_POSITION_X += deltaX;
    }
}
