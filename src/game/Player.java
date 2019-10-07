package game;

import ast.Identifier;
import ast.Number;

public class Player {
    private Identifier playerId;
    private Number number;
    private Projectile projectile;
    private Number health;
    private Number damage;

    public Player(Identifier id) {
        playerId = id;

        projectile = new Projectile();
        health = new Number(1);
        damage = new Number(1);
    }

    public Player(Identifier id, Number number) {
        this(id);
        this.number = number;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public void setHealth(Number health) {
        this.health = health;
    }

    public void setDamage(Number damage) {
        this.damage = damage;
    }
}
