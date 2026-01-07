package logic;

import javax.swing.JPanel;
import panel.MenuPanel;
import panel.GamePanel;
import panel.GameFrame;
import panel.RecordsPanel;

public class SceneManager {

    private GameFrame frame;

    public SceneManager(GameFrame frame) {
        this.frame = frame;
    }

    public void switchScene(GameState state) {
        JPanel panel = null;

        switch (state) {
            case MENU:
                panel = new MenuPanel(frame);
                break;
            case PLAYING:
                panel = new GamePanel(frame);
                break;
            case RECORDS:
                panel = new RecordsPanel(frame);
                break;

        }

        frame.setPanel(panel);
    }
}
