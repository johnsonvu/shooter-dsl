package game;

public class Enemy {
    private String enemyId;
    private  int number;
    private Projectile projectile;

    private int health;
    private int damage;

    public Enemy(String id) {
        enemyId = id;
        number = 1;

        projectile = new Projectile();
        health = 1;
        damage = 1;
    }

    public Enemy(String id, int number) {
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
