package game;

import ast.Identifier;

public class Enemy {
    private Identifier enemyId;
    private  Number number;
    private Projectile projectile;

    private Number health;
    private Number damage;

    public Enemy(Identifier id) {
        enemyId = id;
        number = 1;

        projectile = new Projectile();
        health = 1;
        damage = 1;
    }

    public Enemy(Identifier id, Number number) {
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
