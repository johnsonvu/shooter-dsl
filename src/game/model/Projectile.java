package game.model;


import game.view.Game;
import lib.DIRECTION;

import java.awt.*;

public class Projectile extends GameObject {
    private int number;
    private int damage;
    private DIRECTION dir;

    public Projectile(int x, int y, String id, int damage, DIRECTION dir) {
        super(x, y, id + "_projectile");
        this.number = 1;
        this.damage = damage;
        this.dir = dir;

        image = Game.sprit.loadImage(this);

    }

    public Projectile(int x, int y, String id, int number, int damage, DIRECTION dir) {
        this(x,y, id, damage, dir);
        this.number = number;

        image = Game.sprit.loadImage(this);
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
