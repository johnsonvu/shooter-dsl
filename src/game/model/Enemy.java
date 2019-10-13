package game.model;

import evaluate.protoypes.EnemyProto;

import java.awt.*;

public class Enemy extends GameObject {
    private String enemyId;
    private Projectile projectile;

    public Enemy(EnemyProto proto, String name) {
        super(proto,name);
        this.health = proto.health;
        this.damage = proto.damage;

        //this.projectile = new Projectile(0,0); // is this needed?
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
