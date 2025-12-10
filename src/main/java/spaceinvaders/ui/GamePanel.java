package spaceinvaders.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Renderer renderer;  // Remove javax.swing.Renderer reference

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
        renderer.drawTime(graphics);
        renderer.drawGameOver(graphics);
        renderer.drawWin(graphics);
    }
}