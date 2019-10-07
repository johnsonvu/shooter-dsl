package game.model;

import java.awt.*;

public class Item extends GameObject {
    private String itemId;
    private int number;
    // add coordinate?

    public Item(int x, int y, String id, int number) {
        super(x,y,id);
        itemId = id;
        this.number = number;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
