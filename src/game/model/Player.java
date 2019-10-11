package game.model;

public class Player {
    private String playerId;
    private int number;
    private Projectile projectile;
    private int health;
    private int damage;

    public Player(String id) {
        playerId = id;

        projectile = new Projectile();
        health = 1;
        damage = 1;
    }

    public Player(String id, int number) {
        this(id);
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
}