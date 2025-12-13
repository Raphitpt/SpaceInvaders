package spaceinvaders.ui;

import spaceinvaders.config.GameConfig;
import spaceinvaders.game.GameTimer;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    private GamePanel gamePanel;
    private GameTimer gameTimer;

    public void setupFrame() {
        JFrame frame = new JFrame("🎄 Space Invaders - Christmas Edition ❄️");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GameConfig.getWindowWidth(), GameConfig.getWindowHeight());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Style de Noël pour la fenêtre
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Panneau principal avec décoration de Noël
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(15, 32, 15)); // Vert sapin foncé

        // Bandeau supérieur festif
        JPanel topPanel = createChristmasHeader();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Ajout du panneau de jeu
        gamePanel = new GamePanel();
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // Bandeau inférieur festif
        JPanel bottomPanel = createChristmasFooter();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);

        // Icône de Noël pour la fenêtre (optionnel)
        try {
            frame.setIconImage(createChristmasIcon());
        } catch (Exception e) {
            // Icône par défaut si erreur
        }

        // Démarrage de la boucle de jeu
        gameTimer = new GameTimer(gamePanel);
        frame.setVisible(true);
    }

    private JPanel createChristmasHeader() {
        JPanel header = new JPanel();
        header.setBackground(new Color(200, 20, 20)); // Rouge Noël
        header.setPreferredSize(new Dimension(0, 35));
        header.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JLabel titleLabel = new JLabel("🎅 JOYEUX NOËL 🎁");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        // Ajoute des flocons de neige
        JLabel snow1 = new JLabel("❄️");
        JLabel snow2 = new JLabel("⛄");
        JLabel snow3 = new JLabel("❄️");

        snow1.setFont(new Font("Arial", Font.PLAIN, 20));
        snow2.setFont(new Font("Arial", Font.PLAIN, 20));
        snow3.setFont(new Font("Arial", Font.PLAIN, 20));

        header.add(snow1);
        header.add(titleLabel);
        header.add(snow2);
        header.add(snow3);

        return header;
    }

    private JPanel createChristmasFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(34, 139, 34)); // Vert sapin
        footer.setPreferredSize(new Dimension(0, 30));
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

        // Décorations de Noël
        String[] decorations = {"🎄", "🎁", "⭐", "🔔", "🕯️", "🦌"};
        Font decorFont = new Font("Arial", Font.PLAIN, 18);

        for (String decor : decorations) {
            JLabel decorLabel = new JLabel(decor);
            decorLabel.setFont(decorFont);
            footer.add(decorLabel);
        }

        return footer;
    }

    private Image createChristmasIcon() {
        // Crée une simple icône de sapin de Noël
        int size = 32;
        java.awt.image.BufferedImage icon = new java.awt.image.BufferedImage(
                size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = icon.createGraphics();

        // Anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessine un sapin simple
        g2d.setColor(new Color(34, 139, 34));
        int[] xPoints = {size/2, size/4, 3*size/4};
        int[] yPoints = {size/4, 3*size/4, 3*size/4};
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Étoile en haut
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(size/2 - 3, size/4 - 6, 6, 6);

        g2d.dispose();
        return icon;
    }
}