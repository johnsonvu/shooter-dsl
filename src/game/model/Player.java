package game.model;

import evaluate.protoypes.ProjectileProto;
import game.view.Game;
import lib.DIRECTION;
import lib.KEYINPUTTYPE;
import evaluate.protoypes.PlayerProto;

import java.awt.*;
import java.util.HashMap;

public class Player extends GameObject {
    public Player(PlayerProto proto, String name) {
        super(proto, name);
        this.damage = proto.damage;
        this.health = proto.health;
        image = Game.sprite.loadImage(this);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() { return health; }

    public void shoot() {
        handler.objects.add(new Projectile(new ProjectileProto(id, damage, health), id, DIRECTION.UP));
    }

    @Override
    public void tick() {
        collision();
        act();
    }

    private void collision() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject obj = handler.objects.get(i);
            if (obj instanceof Enemy) {
//                if (this.getBounds().intersects(obj.getBounds()))
//                    gameOver();
            }
        }
    }

    private void act() {
        HashMap<KEYINPUTTYPE, Boolean> map = handler.objectStates.get(this);
        if (map.get(KEYINPUTTYPE.UP)) {
            if (checkBound(x, y, DIRECTION.UP)) {
                move(DIRECTION.UP);
            }
        }

        if (map.get(KEYINPUTTYPE.DOWN)) {
            if (checkBound(x, y, DIRECTION.DOWN))
                move(DIRECTION.DOWN);
        }

        if (map.get(KEYINPUTTYPE.LEFT)) {
            if (checkBound(x, y, DIRECTION.LEFT))
                move(DIRECTION.LEFT);
        }

        if (map.get(KEYINPUTTYPE.RIGHT)) {
            if (checkBound(x, y, DIRECTION.RIGHT))
                move(DIRECTION.RIGHT);
        }

        if (map.get(KEYINPUTTYPE.SHOOT))
            shoot();
    }

    @Override
    public void render(Graphics g) {

    }
}
