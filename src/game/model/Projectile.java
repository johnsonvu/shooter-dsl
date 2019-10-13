
package game.model;


import evaluate.protoypes.ProjectileProto;

import java.awt.*;

public class Projectile extends GameObject {
    private static String DEFAULT_PROJECTILE = "bullet";

    public Projectile(ProjectileProto proto, String name) {
        super(proto, name);
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