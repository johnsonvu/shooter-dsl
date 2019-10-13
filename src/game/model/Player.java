package game.model;

import game.view.Game;
import lib.DIRECTION;

import java.awt.*;

public class Player extends GameObject {
    private int number;
    private Projectile projectile;
    private int health;
    private int damage;

    public Player(int x, int y, String id) {
        super(x,y,id);

        health = 1;
        damage = 1;

        image = Game.sprit.loadImage(this);

    }

    public Player(int x, int y, String id, int number) {
        this(x,y,id);
        this.number = number;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
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

    @Override
    public void tick() {
        collision();
        movement();
    }

    private void collision() {
//        for (int i = 0; i < handler.objects.size(); i++) {
//            GameObject obj = handler.objects.get(i);
//
//
//        }
    }

    private void movement() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }
}
