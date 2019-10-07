package game.model;


import java.awt.*;

public class Projectile extends GameObject {
    private static String DEFAULT_PROJECTILE = "bullet";
    private String projectileId;
    private int number;

    public Projectile(int x, int y) {
        super(x,y,DEFAULT_PROJECTILE);
        this.projectileId = DEFAULT_PROJECTILE;
        this.number = 1;
    }

    public Projectile(int x, int y, String id, int number) {
        this(x,y);
        this.projectileId = id;
        this.number = number;
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
