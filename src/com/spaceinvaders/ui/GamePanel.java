package src.com.spaceinvaders.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    Renderer renderer;

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        renderer.drawShip(graphics);
        renderer.drawBullet(graphics);
        renderer.drawEnemies(graphics);
        renderer.drawScore(graphics);
        renderer.drawGameOver(graphics);
    }
}
