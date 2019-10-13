package game.model;

import game.view.Game;
import lib.DIRECTION;

import java.awt.*;

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
        switch (KEYINPUTTYPE) {
            case UP:
                move(DIRECTION.UP);
                break;
            case DOWN:
                move(DIRECTION.DOWN);
                break;
            case LEFT:
                move(DIRECTION.LEFT);
                break;
            case RIGHT:
                move(DIRECTION.RIGHT);
                break;
            default:
                shoot();
        }
    }

    @Override
    public void render(Graphics g) {

    }
}
