package game.model;

import game.view.Game;
import lib.DIRECTION;

import java.awt.*;

public class Enemy extends GameObject {
    private int number;

    private int health;
    private int damage;

    public Enemy(int x, int y, String id) {
        super(x,y,id);
        this.number = 1;

        this.health = 1;
        this.damage = 1;

        image = Game.sprit.loadImage(this);
    }

    public Enemy(int x, int y, String id, int number) {
        this(x,y,id);
        this.number = number;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void shoot(DIRECTION dir) {
        handler.objects.add(new Projectile(x, y, id, damage, dir));
    }

    public void move(DIRECTION dir) {
        switch (dir) {
            case UP:
                y -= MOVE_CONSTANT;
                break;
            case DOWN:
                y += MOVE_CONSTANT;
                break;
            case LEFT:
                x -= MOVE_CONSTANT;
                break;
            default:
                x += MOVE_CONSTANT;
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }
}
