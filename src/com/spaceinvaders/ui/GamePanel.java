package src.com.spaceinvaders.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Renderer renderer;

    public GamePanel() {
        this.renderer = new Renderer();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        renderer.drawShip(graphics);
        renderer.drawBullet(graphics);
        renderer.drawEnemies(graphics);
        renderer.drawBoss(graphics);
        renderer.drawBossProjectiles(graphics);
        renderer.drawScore(graphics);
        renderer.drawGameOver(graphics);
        renderer.drawWin(graphics);
    }
}
