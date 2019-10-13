package game.model;

import ast.Direction;
import evaluate.Evaluator;
import evaluate.protoypes.ProjectileProto;
import game.view.Game;
import lib.DIRECTION;
import evaluate.protoypes.EnemyProto;
import ui.Main;

import java.awt.*;

public class Enemy extends GameObject {
    public Enemy(EnemyProto proto, String name) {
        super(proto,name);
        this.health = proto.health;
        this.damage = proto.damage;
        this.moveSpeed = 3;
        image = Game.sprite.loadImage(this);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() { return health; }

    public void shoot(DIRECTION dir) {
        Main.gameObjects.add(new Projectile(new ProjectileProto(id, damage, health), id, dir));
    }

    @Override
    public void move(DIRECTION dir){
        switch (dir) {
            case UP:
            case DOWN:
                if (checkBound(x, y, dir)) {
                    //super.move(dir);/
                    this.y += moveSpeed/5;
                } else {
                    Main.gameObjects.remove(this);
                }
                break;
            case LEFT:
                x = (x <= 0) ? Game.getInstance().getWidth() : x - moveSpeed;
                break;
            default:
                System.out.println(x);
                x = (x >= Game.getInstance().getWidth()) ? 0 : x + moveSpeed;
                System.out.println(x);
                break;
        }
    }

    @Override
    public void tick() {
        Evaluator eval = Evaluator.getInstance();
        eval.run(this.proto.behaviour, this);
    }

    @Override
    public void render(Graphics g) {
        int xCenter = x - image.getWidth(null)/2;
        int yCenter = y - image.getHeight(null)/2;
        g.drawImage(image, xCenter,yCenter, null);
    }
}
