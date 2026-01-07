package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Image;

import entity.Player;
import entity.Item;
import entity.KeyItem;
import data.GameDataManager;
import logic.GameState;

public class GamePanel extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int TILE_SIZE = 50;

    // COLOR THEME 
    private static final Color FLOOR_COLOR = new Color(200, 185, 155);
    private static final Color FLOOR_EDGE  = new Color(170, 155, 130);
    private static final Color WALL_COLOR  = new Color(90, 75, 60);
    private static final Color WALL_SHADOW = new Color(70, 55, 40);
    private static final Color BG_COLOR    = new Color(75, 65, 55);

    // MAZES
    private final int[][][] maps = {
        {
            {1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,1,0,0,0,0,1},
            {1,0,1,1,1,0,1,0,1,1,0,1},
            {1,0,1,0,0,0,0,0,0,1,0,1},
            {1,0,1,0,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,1,0,0,0,1},
            {1,1,1,1,1,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,1,0,1},
            {1,0,1,1,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1}
        },
        {
            {1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,1,0,0,0,1,0,1,0,0,1},
            {1,0,1,0,1,0,1,0,1,0,1,1},
            {1,0,0,0,1,0,0,0,1,0,0,1},
            {1,1,1,0,1,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,1,0,0,0,0,1},
            {1,0,1,1,1,0,1,1,1,1,0,1},
            {1,0,0,0,1,0,0,0,0,1,0,1},
            {1,1,1,0,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1}
        },
        {
            {1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,1,0,1,0,1,0,1,1,0,1},
            {1,0,0,0,1,0,0,0,0,1,0,1},
            {1,1,1,0,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,1,0,0,1,0,1},
            {1,0,1,1,1,0,1,0,1,1,0,1},
            {1,0,0,0,1,0,0,0,1,0,0,1},
            {1,1,1,0,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,2,1},
            {1,1,1,1,1,1,1,1,1,1,1,1}
        }
    };

    private int currentMaze = 0;
    private Player player;
    private ArrayList<Item> items = new ArrayList<>();
    private Image doorSprite;

    private long startTime;
    private long pausedTime;
    private long finalTimeMs;

    private boolean timeStopped = false;
    private boolean levelCleared = false;
    private boolean finalMazeCleared = false;
    private boolean pickingItem = false;
    private boolean finishing = false;

    private GameFrame frame;

    public GamePanel(GameFrame frame) {
        this.frame = frame;

        setPreferredSize(new Dimension(
            maps[0][0].length * TILE_SIZE,
            maps[0].length * TILE_SIZE
        ));

        setFocusable(true);
        loadMaze(0);

        try {
            doorSprite = ImageIO.read(
                getClass().getResource("/assets/door.png")
            );
        } catch (Exception e) {
            System.out.println("Gagal load sprite door");
        }

        startTime = System.currentTimeMillis();

        addKeyListener(this);
        SwingUtilities.invokeLater(this::requestFocusInWindow);

        new Timer(1000 / 60, e -> repaint()).start();
    }

    private int[][] map() {
        return maps[currentMaze];
    }

    private void loadMaze(int index) {
        currentMaze = index;
        player = new Player(1, 1, TILE_SIZE);
        items.clear();
        items.add(new KeyItem(5 * TILE_SIZE, 1 * TILE_SIZE, TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // BACKGROUND
        g2.setColor(BG_COLOR);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // MAP
        for (int r = 0; r < map().length; r++) {
            for (int c = 0; c < map()[0].length; c++) {
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                if (map()[r][c] == 1) {
                    g2.setColor(WALL_SHADOW);
                    g2.fillRect(x + 3, y + 3, TILE_SIZE, TILE_SIZE);
                    g2.setColor(WALL_COLOR);
                    g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                } else {
                    g2.setColor(FLOOR_COLOR);
                    g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    g2.setColor(FLOOR_EDGE);
                    g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
                }

                if (map()[r][c] == 2 && doorSprite != null) {
                    g2.drawImage(doorSprite, x, y, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        for (Item item : items) item.draw(g2);
        player.draw(g2);

        // TIMER HUD
        long elapsedMs = timeStopped
                ? finalTimeMs
                : System.currentTimeMillis() - startTime;

        long minutes = elapsedMs / 60000;
        long seconds = (elapsedMs / 1000) % 60;
        long millis  = (elapsedMs / 10) % 100;

        String hud = "MAZE " + (currentMaze + 1) + " / " + maps.length +
                     "   TIME " + String.format("%02d.%02d.%02d",
                     minutes, seconds, millis);

        g2.setFont(new Font("Arial", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        int hudW = fm.stringWidth(hud) + 30;
        int hudX = (getWidth() - hudW) / 2;

        g2.setColor(new Color(50, 40, 30, 200));
        g2.fillRoundRect(hudX, 10, hudW, 26, 14, 14);
        g2.setColor(Color.WHITE);
        g2.drawString(hud, hudX + 15, 28);

        // OVERLAY
        if (levelCleared) {
            g2.setColor(new Color(0, 0, 0, 170));
            g2.fillRect(0, 0, getWidth(), getHeight());

            String title = finalMazeCleared
                    ? "ALL MAZES CLEARED"
                    : "MAZE " + (currentMaze + 1) + " CLEARED";

            g2.setFont(new Font("Arial", Font.BOLD, 28));
            FontMetrics fmTitle = g2.getFontMetrics();
            int titleX = (getWidth() - fmTitle.stringWidth(title)) / 2;
            int titleY = getHeight() / 2 - 10;

            g2.setColor(Color.WHITE);
            g2.drawString(title, titleX, titleY);

            String sub = "PRESS ENTER TO CONTINUE";
            g2.setFont(new Font("Arial", Font.PLAIN, 14));
            FontMetrics fmSub = g2.getFontMetrics();
            int subX = (getWidth() - fmSub.stringWidth(sub)) / 2;
            int subY = titleY + 30;

            g2.drawString(sub, subX, subY);
        }
    }

    private void checkItemCollision() {
        if (pickingItem || levelCleared) return;

        for (Item item : items) {
            if (player.isInsideTile(item.getRow(TILE_SIZE), item.getCol(TILE_SIZE), TILE_SIZE)) {
                pickingItem = true;
                new Timer(120, e -> {
                    player.obtainKey();
                    items.remove(item);
                    pickingItem = false;
                }) {{ setRepeats(false); }}.start();
                break;
            }
        }
    }

    private void checkWin() {
        if (finishing || levelCleared) return;

        if (map()[player.getRow()][player.getCol()] == 2 && player.hasKey()) {

            pausedTime = System.currentTimeMillis();
            finalTimeMs = pausedTime - startTime;

            timeStopped = true;
            levelCleared = true;
            finishing = true;
            finalMazeCleared = (currentMaze == maps.length - 1);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (levelCleared && e.getKeyCode() == KeyEvent.VK_ENTER) {

            levelCleared = false;
            timeStopped = false;
            startTime += (System.currentTimeMillis() - pausedTime);

            if (finalMazeCleared) {

                GameDataManager.saveScore(
                        frame.getPlayerName(),
                        (int) (finalTimeMs / 1000)
                );
                frame.getSceneManager().switchScene(GameState.RECORDS);

            } else {
                loadMaze(currentMaze + 1);
                finishing = false;
            }

            finalMazeCleared = false;
            return;
        }

        if (levelCleared) return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:    player.move(-1, 0, map()); break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:  player.move(1, 0, map());  break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:  player.move(0, -1, map()); break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT: player.move(0, 1, map());  break;
        }

        checkItemCollision();
        checkWin();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
