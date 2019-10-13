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
        this.damage = proto.damage;
        this.dir = dir;
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
                    //System.out.println("Enemy = " + e.health);
                    //System.out.println("proj = " + damage);
                    e.setHealth(e.getHealth() - damage);
                    if(e.getHealth() <= 0){
                        Main.gameObjects.remove(e);
                    }
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
        g.drawImage(image, x,y, null);
    }
}