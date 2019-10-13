package game.model;

import game.view.Game;

import java.awt.*;

public class Item extends GameObject {
    public Item(int x, int y, String id, int number) {
        super(x,y,id);
        this.number = number;

        image = Game.sprite.loadImage(this);

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
