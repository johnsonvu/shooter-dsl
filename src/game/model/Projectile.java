package game.model;

import game.view.Game;
import lib.enums.Direction;

import evaluate.protoypes.ProjectileProto;
import ui.Main;

import java.awt.*;

public class Projectile extends GameObject {
    private Direction dir;
    private GameObject source;

    public Projectile(ProjectileProto proto, String name, Direction dir, GameObject source) {
        super(proto, name);
        this.damage = proto.damage;
        this.dir = dir;
        this.source = source;
        this.moveSpeed = 10;
        image = Game.sprite.loadImage(this);
    }

    public int getDamage() { return damage; }

    @Override
    public void tick() {
        collision();
        move(dir);
    }

    private void collision() {
        boolean remove = false;
        for (int i = 0; i < Main.gameObjects.size(); i++) {
            GameObject obj = Main.gameObjects.get(i);

            // player + enemy projectiles do damage to players
            if (obj instanceof Player) {
                if (this.getBounds().intersects(obj.getBounds())) {
                    Player p = (Player) obj;
                    if(p.getHealth() - damage <= 0) {
                        p.setHealth(0);
                    } else {
                        p.setHealth(p.getHealth() - damage);
                    }
                    remove = true;
                    break;
                }
            }

            // player projectiles do damage to enemies
            if (obj instanceof Enemy && this.source instanceof Player) {
                if (this.getBounds().intersects(obj.getBounds())) {
                    Enemy e = (Enemy) obj;
                    e.setHealth(e.getHealth() - damage);
                    if(e.getHealth() <= 0){
                        Main.gameObjects.remove(e);
                    }
                    remove = true;
                    break;
                }
            }

            // if player projectile hits enemy projectile, remove both
            if (obj instanceof Projectile && this.source instanceof Player && ((Projectile) obj).source instanceof Enemy) {
                if (this.getBounds().intersects(obj.getBounds())) {
                    Projectile e = (Projectile) obj;
                    Main.gameObjects.remove(e);
                    remove = true;
                    break;
                }
            }
        }
        // remove projectile once collision occurs
        if (remove)
            Main.gameObjects.remove(this);
    }

    @Override
    public void move(Direction dir) {
        if (checkBound(x, y, dir)) {
            super.move(dir);
        } else {
            Main.gameObjects.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        int xCenter = x - image.getWidth(null)/2;
        int yCenter = y - image.getHeight(null)/2;
        g.drawImage(image, xCenter,yCenter, null);
    }
}