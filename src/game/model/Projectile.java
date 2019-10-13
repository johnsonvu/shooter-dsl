package game.model;

import game.view.Game;
import lib.DIRECTION;

import evaluate.protoypes.ProjectileProto;
import ui.Main;

import java.awt.*;

public class Projectile extends GameObject {
    private DIRECTION dir;

    public Projectile(ProjectileProto proto, String name, DIRECTION dir) {
        super(proto, name);
        this.dir = dir;
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

            if (obj instanceof Player) {
                if (this.getBounds().intersects(obj.getBounds())) {
                    Player p = (Player) obj;
                    p.setHealth(p.getHealth() - damage);
                    remove = true;
                    break;
                }
            }

            if (obj instanceof Enemy) {
                if (this.getBounds().intersects(obj.getBounds())) {
                    Enemy e = (Enemy) obj;
                    e.setHealth(e.getHealth() - damage);
                    remove = true;
                    break;
                }
            }
        }
        if (remove)
            Main.gameObjects.remove(this);
    }

    @Override
    public void move(DIRECTION dir) {
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