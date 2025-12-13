package spaceinvaders.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = GameConfig.class
                .getClassLoader()
                .getResourceAsStream("game.properties")) {

            if (input == null) {
                throw new RuntimeException("Fichier game.properties introuvable dans le classpath");
            }

            props.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger la configuration", e);
        }
    }

    public static int getWindowWidth() {
        return Integer.parseInt(props.getProperty("window.width"));
    }

    public static int getWindowHeight() {
        return Integer.parseInt(props.getProperty("window.height"));
    }

    public static int getShipSpeed() {
        return Integer.parseInt(props.getProperty("ship.speed"));
    }

    public static int getShipStartX(int shipWidth) {
        return (getWindowWidth() - shipWidth) / 2;
    }

    public static int getEnemiesGridStartX(int gridWidth) {
        return (getWindowWidth() - gridWidth) / 2;
    }
}
