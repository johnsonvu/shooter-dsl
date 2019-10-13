package game.model;

import evaluate.protoypes.PlayerProto;

import java.awt.*;

public class Player extends GameObject {
    private String playerId;
    private int number;
    private Projectile projectile;

    public Player(PlayerProto proto, String name) {
        super(proto, name);
        this.damage = proto.damage;
        this.health = proto.health;
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
