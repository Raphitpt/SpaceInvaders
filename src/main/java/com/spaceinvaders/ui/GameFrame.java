package src.main.java.com.spaceinvaders.ui;

import src.main.java.com.spaceinvaders.config.GameConfig;

import javax.swing.*;

public class GameFrame {
    GamePanel gamePanel;

    public void setupFrame() {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
