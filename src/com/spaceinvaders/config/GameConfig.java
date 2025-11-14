package src.com.spaceinvaders.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {
    private static String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String appConfigPath = rootPath + "app.properties";

    private static final Properties props = new Properties();
    static {
        try {
            props.load(new FileInputStream(appConfigPath));
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
}
