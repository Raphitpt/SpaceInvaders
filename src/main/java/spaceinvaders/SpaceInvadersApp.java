package spaceinvaders;

import spaceinvaders.ui.GameFrame;

public class SpaceInvadersApp {

    public static void main(String[] args) {
        // Création de la frame avec l'ajout du panneau du jeu
        GameFrame gameFrame = new GameFrame();
        gameFrame.setupFrame();
    }
}