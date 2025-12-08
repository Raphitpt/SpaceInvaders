package spaceinvaders.ui;

import spaceinvaders.config.GameConfig;
import spaceinvaders.game.GameTimer;

import javax.swing.*;

public class GameFrame {
    private GamePanel gamePanel;
    private GameTimer gameTimer;

    public void setupFrame() {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameConfig.getWindowWidth(), GameConfig.getWindowHeight());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Ajout du panneau de jeu
        gamePanel = new GamePanel();
        frame.add(gamePanel);

        // Démarrage de la boucle de jeu
        gameTimer = new GameTimer(gamePanel);
        frame.setVisible(true);
    }
}