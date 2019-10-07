package game.model;

import java.awt.*;

public class Enemy extends GameObject {
    private String enemyId;
    private int number;
    private Projectile projectile;

    private int health;
    private int damage;

    public Enemy(int x, int y, String id) {
        super(x,y,id);
        this.enemyId = id;
        this.number = 1;

        this.projectile = new Projectile(0,0); // is this needed?
        this.health = 1;
        this.damage = 1;
    }

    public Enemy(int x, int y, String id, int number) {
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
