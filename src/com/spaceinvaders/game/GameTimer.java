package src.com.spaceinvaders.game;

import src.com.spaceinvaders.input.InputHandler;

import javax.swing.*;

public class GameTimer {

    private final Timer gameLoopTimer;
    private final GameEngine gameEngine;
    private final JPanel gamePanel;

    public GameTimer(JPanel gamePanel) {
        this.gamePanel = gamePanel;

        InputHandler inputHandler = new InputHandler(gamePanel);
        this.gameEngine = new GameEngine(this, inputHandler);

        gameLoopTimer = new Timer(50, event -> {
            gameEngine.update();
            gamePanel.repaint();
        });

        gameLoopTimer.start();
    }

    public void stopGame() {
        gameLoopTimer.stop();
    }
}
