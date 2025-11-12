package src.com.spaceinvaders.ui;

import src.com.spaceinvaders.config.GameConfig;

import javax.swing.*;

public class GameFrame {
    GamePanel gamePanel;

    public void setupFrame() {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameConfig.getWindowWidth(), GameConfig.getWindowHeight());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
