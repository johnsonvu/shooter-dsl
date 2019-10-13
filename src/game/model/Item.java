package game.model;

import game.view.Game;
import evaluate.protoypes.ItemProto;

import java.awt.*;

public class Item extends GameObject {
    public Item(ItemProto proto, String name) {
        super(proto, name);
        image = Game.sprite.loadImage(this);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        int xCenter = x - image.getWidth(null)/2;
        int yCenter = y - image.getHeight(null)/2;
        g.drawImage(image, xCenter,yCenter, null);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
