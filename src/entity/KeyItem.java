package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import javax.imageio.ImageIO;

public class KeyItem extends Item {

    private Image sprite;

    public KeyItem(int x, int y, int size) {
        super(x, y, size);

        try {
            sprite = ImageIO.read(
                getClass().getResource("/assets/key.png")
            );
        } catch (Exception e) {
            System.out.println("Gagal load sprite key");
        }
    }

    @Override
    public void draw(Graphics g) {

        // KEY < TILE
        int keySize = size / 2;
        int offset = (size - keySize) / 2;

        if (sprite != null) {
            g.drawImage(
                sprite,
                x + offset,
                y + offset,
                keySize,
                keySize,
                null
            );
        } else {
            g.setColor(Color.YELLOW);
            g.fillRect(
                x + offset,
                y + offset,
                keySize,
                keySize
            );
        }
    }
}

