package entity;

import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Player extends GameObject {

    private int row;
    private int col;
    private int tileSize;

    private Image sprite;

    private int score = 0;
    private boolean hasKey = false;

    public Player(int row, int col, int tileSize) {
        super(col * tileSize, row * tileSize, tileSize);
        this.row = row;
        this.col = col;
        this.tileSize = tileSize;

        try {
            sprite = ImageIO.read(
                getClass().getResource("/assets/player.png")
            );
        } catch (Exception e) {
            System.out.println("Gagal load sprite player");
        }
    }

    // GETTER
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getScore() {
        return score;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public void obtainKey() {
        hasKey = true;
    }

    // PLAYER MOVEMENT
    public void move(int dRow, int dCol, int[][] map) {

        int newRow = row + dRow;
        int newCol = col + dCol;

        if (newRow < 0 || newCol < 0 ||
            newRow >= map.length ||
            newCol >= map[0].length) {
            return;
        }

        if (map[newRow][newCol] == 1) {
            return;
        }

        row = newRow;
        col = newCol;

        x = col * tileSize;
        y = row * tileSize;

        score += 10;
    }

    // CHECK PLAYER BENAR-BENAR DI DALAM TILE 
    public boolean isInsideTile(int tileRow, int tileCol, int tileSize) {

        int centerX = x + size / 2;
        int centerY = y + size / 2;

        int tileStartX = tileCol * tileSize;
        int tileStartY = tileRow * tileSize;

        int tileEndX = tileStartX + tileSize;
        int tileEndY = tileStartY + tileSize;

        return centerX >= tileStartX &&
               centerX < tileEndX &&
               centerY >= tileStartY &&
               centerY < tileEndY;
    }

    @Override
    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, size, size, null);
        } else {
            g.fillRect(x, y, size, size);
        }
    }
}
