package game.model;

import evaluate.Evaluator;
import game.view.Game;
import lib.DIRECTION;

import java.awt.*;

public class Enemy extends GameObject {
    private int number;

    private int health;
    private int damage;

    public Enemy(int x, int y, String id) {
        super(x,y,id);
        this.number = 1;

        this.health = 1;
        this.damage = 1;

        image = Game.sprit.loadImage(this);
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
    public void tick() {
        Evaluator eval = Evaluator.getInstance();
        eval.run(this.behaviour, this);
    }

    @Override
    public void render(Graphics g) {

    }
}
