package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int size;

    public GameObject(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public abstract void draw(Graphics g);
}



