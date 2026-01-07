package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Item {

    protected int x;
    protected int y;
    protected int size;

    public Item(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getRow(int tileSize) {
        return y / tileSize;
    }

    public int getCol(int tileSize) {
        return x / tileSize;
    }

    public abstract void draw(Graphics g);
}
