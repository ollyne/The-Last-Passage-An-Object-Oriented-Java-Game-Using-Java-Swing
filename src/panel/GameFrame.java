package panel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import logic.SceneManager;
import logic.GameState;

public class GameFrame extends JFrame {

    private SceneManager sceneManager;
    private String playerName;

    public GameFrame() {
        setTitle("THE LAST PASSAGE");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        sceneManager = new SceneManager(this);
        sceneManager.switchScene(GameState.MENU);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // DIPANGGIL DARI MENU
    public void startGame(String name) {
        this.playerName = name;
        sceneManager.switchScene(GameState.PLAYING);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
        pack();
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
