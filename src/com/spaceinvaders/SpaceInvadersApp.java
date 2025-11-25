package src.com.spaceinvaders;

import src.com.spaceinvaders.ui.GameFrame;

import javax.swing.*;

public class SpaceInvadersApp {

    public static void main(String[] args) {
        // Création de la frame avec l'ajout du panneau du jeu
        GameFrame gameFrame = new GameFrame();
        gameFrame.setupFrame();
    }
}