package src.com.spaceinvaders.game;

import javax.swing.*;
import java.awt.*;

public class GameTimer extends JPanel {

    private final Timer gameLoopTimer;
    private final GameEngine gameEngine;

    public GameTimer() {
        this.gameEngine = new GameEngine(this);

        gameLoopTimer = new Timer(50, event -> {
            gameEngine.update();
            repaint();
        });

        gameLoopTimer.start();
    }

    public void stopGame() {
        gameLoopTimer.stop();
    }
}
