package spaceinvaders.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spaceinvaders.config.GameConfig;
import spaceinvaders.game.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class GameFrameTest {

    private GameFrame gameFrame;

    @BeforeEach
    void setUp() {
        gameFrame = new GameFrame();
    }

    @AfterEach
    void tearDown() {
        // Fermer toutes les fenêtres créées pendant les tests
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            window.dispose();
        }
    }

    @Test
    void testSetupFrame_shouldCreateFrame() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame, "JFrame should be created");
        assertTrue(jFrame.isVisible(), "Frame should be visible");
    }

    @Test
    void testSetupFrame_shouldHaveCorrectTitle() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);
        assertEquals("🎄 Space Invaders - Christmas Edition ❄️", jFrame.getTitle());
    }

    @Test
    void testSetupFrame_shouldHaveCorrectSize() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);
        assertEquals(GameConfig.getWindowWidth(), jFrame.getWidth());
        assertEquals(GameConfig.getWindowHeight(), jFrame.getHeight());
    }

    @Test
    void testSetupFrame_shouldNotBeResizable() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);
        assertFalse(jFrame.isResizable(), "Frame should not be resizable");
    }

    @Test
    void testSetupFrame_shouldHaveExitOnCloseOperation() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);
        assertEquals(JFrame.EXIT_ON_CLOSE, jFrame.getDefaultCloseOperation());
    }

    @Test
    void testSetupFrame_shouldInitializeGamePanel() throws Exception {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        GamePanel gamePanel = getGamePanel(frame);
        assertNotNull(gamePanel, "GamePanel should be initialized");
    }

    @Test
    void testSetupFrame_shouldInitializeGameTimer() throws Exception {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        GameTimer gameTimer = getGameTimer(frame);
        assertNotNull(gameTimer, "GameTimer should be initialized");
    }

    @Test
    void testSetupFrame_shouldHaveMainPanelWithBorderLayout() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);
        Container contentPane = jFrame.getContentPane();
        Component mainPanel = contentPane.getComponent(0);
        assertTrue(mainPanel instanceof JPanel);
        assertTrue(((JPanel) mainPanel).getLayout() instanceof BorderLayout);
    }

    @Test
    void testSetupFrame_shouldHaveChristmasThemeBackground() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);
        Container contentPane = jFrame.getContentPane();
        JPanel mainPanel = (JPanel) contentPane.getComponent(0);
        assertEquals(new Color(15, 32, 15), mainPanel.getBackground());
    }

    @Test
    void testSetupFrame_shouldHaveThreePanels() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);
        Container contentPane = jFrame.getContentPane();
        JPanel mainPanel = (JPanel) contentPane.getComponent(0);
        assertEquals(3, mainPanel.getComponentCount(), "Should have header, game panel, and footer");
    }

    @Test
    void testCreateChristmasHeader_shouldReturnPanel() throws Exception {
        // Given
        Method method = GameFrame.class.getDeclaredMethod("createChristmasHeader");
        method.setAccessible(true);

        // When
        JPanel header = (JPanel) method.invoke(gameFrame);

        // Then
        assertNotNull(header);
        assertEquals(new Color(200, 20, 20), header.getBackground());
        assertEquals(35, header.getPreferredSize().height);
    }

    @Test
    void testCreateChristmasHeader_shouldContainChristmasElements() throws Exception {
        // Given
        Method method = GameFrame.class.getDeclaredMethod("createChristmasHeader");
        method.setAccessible(true);

        // When
        JPanel header = (JPanel) method.invoke(gameFrame);

        // Then
        assertTrue(header.getComponentCount() > 0, "Header should contain components");

        // Vérifier qu'il y a des JLabels
        boolean hasLabels = false;
        for (Component comp : header.getComponents()) {
            if (comp instanceof JLabel) {
                hasLabels = true;
                break;
            }
        }
        assertTrue(hasLabels, "Header should contain JLabels");
    }

    @Test
    void testCreateChristmasFooter_shouldReturnPanel() throws Exception {
        // Given
        Method method = GameFrame.class.getDeclaredMethod("createChristmasFooter");
        method.setAccessible(true);

        // When
        JPanel footer = (JPanel) method.invoke(gameFrame);

        // Then
        assertNotNull(footer);
        assertEquals(new Color(34, 139, 34), footer.getBackground());
        assertEquals(30, footer.getPreferredSize().height);
    }

    @Test
    void testCreateChristmasFooter_shouldContainSixDecorations() throws Exception {
        // Given
        Method method = GameFrame.class.getDeclaredMethod("createChristmasFooter");
        method.setAccessible(true);

        // When
        JPanel footer = (JPanel) method.invoke(gameFrame);

        // Then
        assertEquals(6, footer.getComponentCount(), "Footer should contain 6 decorations");
    }

    @Test
    void testCreateChristmasIcon_shouldReturnImage() throws Exception {
        // Given
        Method method = GameFrame.class.getDeclaredMethod("createChristmasIcon");
        method.setAccessible(true);

        // When
        Image icon = (Image) method.invoke(gameFrame);

        // Then
        assertNotNull(icon);
        assertTrue(icon.getWidth(null) > 0, "Icon should have width");
        assertTrue(icon.getHeight(null) > 0, "Icon should have height");
    }

    @Test
    void testCreateChristmasIcon_shouldHaveCorrectDimensions() throws Exception {
        // Given
        Method method = GameFrame.class.getDeclaredMethod("createChristmasIcon");
        method.setAccessible(true);

        // When
        Image icon = (Image) method.invoke(gameFrame);

        // Then
        assertEquals(32, icon.getWidth(null));
        assertEquals(32, icon.getHeight(null));
    }

    @Test
    void testSetupFrame_shouldCenterFrameOnScreen() {
        // Given
        GameFrame frame = new GameFrame();

        // When
        frame.setupFrame();

        // Then
        JFrame jFrame = findCreatedFrame();
        assertNotNull(jFrame);

        //
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point location = jFrame.getLocation();

        int expectedX = (screenSize.width - jFrame.getWidth()) / 2;
        int expectedY = (screenSize.height - jFrame.getHeight()) / 2;

        // Tolérance de 50 pixels (pour tenir compte des barres de tâches, etc.)
        assertTrue(Math.abs(location.x - expectedX) < 50, "Frame should be horizontally centered");
        assertTrue(Math.abs(location.y - expectedY) < 50, "Frame should be vertically centered");
    }

    // Helper methods
    private JFrame findCreatedFrame() {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame && window.isVisible()) {
                return (JFrame) window;
            }
        }
        return null;
    }

    private GamePanel getGamePanel(GameFrame gameFrame) throws Exception {
        Field field = GameFrame.class.getDeclaredField("gamePanel");
        field.setAccessible(true);
        return (GamePanel) field.get(gameFrame);
    }

    private GameTimer getGameTimer(GameFrame gameFrame) throws Exception {
        Field field = GameFrame.class.getDeclaredField("gameTimer");
        field.setAccessible(true);
        return (GameTimer) field.get(gameFrame);
    }
}