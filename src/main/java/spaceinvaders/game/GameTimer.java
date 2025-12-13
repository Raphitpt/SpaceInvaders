package spaceinvaders.game;

import spaceinvaders.input.InputHandler;

import javax.swing.*;

public class GameTimer {

    private final Timer gameLoopTimer;
    private final GameEngine gameEngine;
    private final JPanel gamePanel;

    private static int elapsedSeconds = 0;
    private int msAccumulator = 0;

    public GameTimer(JPanel gamePanel) {
        this.gamePanel = gamePanel;

        InputHandler inputHandler = new InputHandler(gamePanel);
        this.gameEngine = new GameEngine(this, inputHandler);

        gameLoopTimer = new Timer(50, event -> {

            gameEngine.update();
            gamePanel.repaint();


            msAccumulator += 50;

            if (msAccumulator >= 1000) {
                elapsedSeconds++;
                msAccumulator = 0;
            }
        });

        gameLoopTimer.start();
    }

    public void stopGame() {
        gameLoopTimer.stop();
    }

    public static int getElapsedSeconds() {
        return elapsedSeconds;
    }
}
