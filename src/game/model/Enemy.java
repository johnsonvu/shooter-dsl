package game.model;

import evaluate.Evaluator;
import game.view.Game;
import lib.DIRECTION;

import java.awt.*;

import static lib.DIRECTION.DOWN;

public class Enemy extends GameObject {
    public Enemy(int x, int y, String id) {
        super(x,y,id);
        this.number = 1;

        this.health = 1;
        this.damage = 1;

        image = Game.sprite.loadImage(this);
    }

    public Enemy(int x, int y, String id, int number) {
        this(x,y,id);
        this.number = number;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() { return health; }

    public void shoot(DIRECTION dir) {
        handler.objects.add(new Projectile(x, y, id, damage, dir));
    }

    @Override
    public void move(DIRECTION dir){
        switch (dir) {
            case UP:
            case DOWN:
                if (checkBound(x, y, dir)) {
                    super.move(dir);
                } else {
                    handler.objects.remove(this);
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
        eval.run(this.behaviour, this);
    }

    @Override
    public void render(Graphics g) {

    }
}
