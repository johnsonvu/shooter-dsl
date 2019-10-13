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
    public void tick() {
//        Evaluator eval = Evaluator.getInstance();
//        eval.run(this.behaviour, this);
        move(DIRECTION.RIGHT);
    }

    @Override
    public void render(Graphics g) {
        int xCenter = x - image.getWidth(null)/2;
        int yCenter = y - image.getHeight(null)/2;
        g.drawImage(image, xCenter,yCenter, null);
    }
}
