package game;


public class Projectile {
    private  static String DEFAULT_PROJECTILE = "bullet";

    private String projectileId;
    private int number;

    public Projectile() {
        projectileId = DEFAULT_PROJECTILE;
        number = 1;
    }

    public Projectile(String id) {
        projectileId = id;
        number = 1;
    }

    public Projectile(String id, int number) {
        this(id);
        this.number = number;
    }
}
