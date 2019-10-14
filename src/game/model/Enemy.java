package game.model;

import ast.Direction;
import evaluate.Evaluator;
import evaluate.protoypes.ProjectileProto;
import game.view.Game;
import lib.DIRECTION;
import evaluate.protoypes.EnemyProto;
import ui.Main;

import java.awt.*;

import static lib.Util.randomInt;

public class Enemy extends GameObject {
    private int shootingDelay = 40;
    private int shootingCounter = 0;
    private int shootingSpeed = 3;

    public Enemy(EnemyProto proto, String name) {
        super(proto,name);
        this.health = proto.health;
        this.damage = proto.damage;
        this.moveSpeed = 3;

        // randomly spawn in the top quarter of the map
        super.x = randomInt(100, Game.getInstance().getWidth() - 100);
        super.y = randomInt(100, Game.getInstance().getHeight()/4);
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
        if (shootingCounter == shootingDelay) {
            Projectile p = new Projectile(new ProjectileProto(id, damage, health), id, dir, this);
            p.x = x;
            p.y = y + image.getHeight();
            p.moveSpeed = this.shootingSpeed;
            Main.gameObjects.add(p);
            shootingCounter = 0;
        } else {
            shootingCounter++;
        }
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
                x = (x >= Game.getInstance().getWidth()) ? 0 : x + moveSpeed;
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
