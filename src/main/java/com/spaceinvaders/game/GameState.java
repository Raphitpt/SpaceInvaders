package src.main.java.com.spaceinvaders.game;

import java.awt.*;
import java.util.ArrayList;

public class GameState {
    public static int SHIP_POSITION_X = 250;
    public static int SHIP_POSITION_Y = 450;

    public static final ArrayList<Point> shotsCount = new ArrayList<>();
    public static final ArrayList<Point> enemiesCount = new ArrayList<>();

    public static int score = 0;
    public static int timer = 0;
}
