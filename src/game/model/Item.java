package game.model;

import evaluate.protoypes.ItemProto;

import java.awt.*;

public class Item extends GameObject {
    // add coordinate?

    public Item(ItemProto proto, String name) {
        super(proto, name);
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
