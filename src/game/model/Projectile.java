
package game.model;


import java.awt.*;

public class Projectile extends GameObject {
    private static String DEFAULT_PROJECTILE = "bullet";
    private String projectileId;
    private int number;

    public Projectile(int x, int y) {
        super(x, y, DEFAULT_PROJECTILE);
        this.projectileId = DEFAULT_PROJECTILE;
        this.number = 1;
    }

    public Projectile(String id) {
        this(1, 1);
        this.projectileId = id;
    }
}