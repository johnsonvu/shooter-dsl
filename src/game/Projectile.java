package game;

import ast.Identifier;

public class Projectile {
    private  static Identifier DEFAULT_PROJECTILE = new Identifier("bullet");

    private Identifier projectileId;

    public Projectile() {
        projectileId = DEFAULT_PROJECTILE;
    }

    public Projectile(Identifier id) {
        projectileId = id;
    }
}
