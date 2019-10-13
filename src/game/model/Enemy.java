package game.model;

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
                    //super.move(dir); // too fast
                    this.y = y + MOVE_CONSTANT/5;
                } else {
                    Main.gameObjects.remove(this);
                }
                break;
            default:
                super.move(dir);
                x = x % Game.getInstance().getWidth();
        }
    }

    @Override
    public void tick() {
        Evaluator eval = Evaluator.getInstance();
        eval.run(this.proto.behaviour, this);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x,y, null);
    }
}
