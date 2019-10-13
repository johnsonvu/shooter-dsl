package game.model;

import game.view.Game;
import lib.DIRECTION;
import lib.KEYINPUTTYPE;

import java.awt.*;
import java.util.HashMap;

import static lib.KEYINPUTTYPE.*;

public class Player extends GameObject {
    private int number;
    private int health;
    private int damage;

    public Player(int x, int y, String id) {
        super(x,y,id);

        health = 1;
        damage = 1;

        image = Game.sprit.loadImage(this);

    }

    public Player(int x, int y, String id, int number) {
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

    public void shoot() {
        handler.objects.add(new Projectile(x, y, id, damage, DIRECTION.UP));
    }

    @Override
    public void tick() {
        //collision();
        act();
    }

//    private void collision() {
//        for (int i = 0; i < handler.objects.size(); i++) {
//            GameObject obj = handler.objects.get(i);
//            if (obj instanceof Projectile) {
//                if (this.getBounds().intersects(obj.getBounds()))
//                    health -= ((Projectile) obj).getDamage();
//            }
//            if (obj instanceof Enemy) {
//                if (this.getBounds().intersects(obj.getBounds()))
//                    health = 0;
//            }
//
//        }
//    }

    private void act() {
        HashMap<KEYINPUTTYPE, Boolean> map = handler.objectStates.get(this);
        if (map.get(KEYINPUTTYPE.UP))
           move(DIRECTION.UP);

        if (map.get((KEYINPUTTYPE.DOWN)))
            move(DIRECTION.DOWN);

        if (map.get((KEYINPUTTYPE.LEFT)))
            move(DIRECTION.LEFT);

        if (map.get((KEYINPUTTYPE.RIGHT)))
            move(DIRECTION.RIGHT);
    }

    @Override
    public void render(Graphics g) {

    }
}
