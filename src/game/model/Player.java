package game.model;

import evaluate.protoypes.ProjectileProto;
import game.controller.Handler;
import game.view.Game;
import lib.DIRECTION;
import lib.KEYINPUTTYPE;
import evaluate.protoypes.PlayerProto;
import ui.Main;

import java.awt.*;
import java.util.HashMap;

import static lib.Util.randomInt;

public class Player extends GameObject {
    private int shootingDelay = 4;
    private int shootingCounter = 0;
    private int shootingSpeed = 7;

    public Player(PlayerProto proto, String name) {
        super(proto, name);
        this.damage = proto.damage;
        this.health = proto.health;
        this.moveSpeed = 6;

        // spawn at middle of the bottom of the game
        super.x = Game.getInstance().getWidth()/2;
        super.y = Game.getInstance().getHeight() - 100;
        image = Game.sprite.loadImage(this);

        Game.getInstance().getHandler().objectStates.put(this, new HashMap<KEYINPUTTYPE, Boolean>());
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() { return health; }

    public void shoot() {
        if (shootingCounter == shootingDelay) {
            Projectile p = new Projectile(new ProjectileProto(id, damage, health), id, DIRECTION.UP, this);
            // set projectile to shoot from player
            p.x = x;
            p.y = y - p.image.getHeight();
            p.moveSpeed = this.shootingSpeed;
            Main.gameObjects.add(p);
            shootingCounter = 0;
        } else {
            shootingCounter++;
        }
    }

    @Override
    public void tick() {
        collision();
        act();
    }

    private void collision() {
        for (int i = 0; i < Main.gameObjects.size(); i++) {
            GameObject obj = Main.gameObjects.get(i);
            if (obj instanceof Enemy) {
//                if (this.getBounds().intersects(obj.getBounds()))
//                    gameOver();
            }
        }
    }

    private void act() {
        HashMap<KEYINPUTTYPE, Boolean> map = Game.getInstance().getHandler().objectStates.get(this);
        if (map.containsKey(KEYINPUTTYPE.UP) && map.get(KEYINPUTTYPE.UP)) {
            if (checkBound(x, y, DIRECTION.UP))
                move(DIRECTION.UP);
        }

        if (map.containsKey(KEYINPUTTYPE.DOWN) && map.get(KEYINPUTTYPE.DOWN)) {
            if (checkBound(x, y, DIRECTION.DOWN))
                move(DIRECTION.DOWN);
        }

        if (map.containsKey(KEYINPUTTYPE.LEFT) && map.get(KEYINPUTTYPE.LEFT)) {
            move(DIRECTION.LEFT);
        }

        if (map.containsKey(KEYINPUTTYPE.RIGHT) && map.get(KEYINPUTTYPE.RIGHT)) {
            move(DIRECTION.RIGHT);
        }

        if (map.containsKey(KEYINPUTTYPE.SHOOT) && map.get(KEYINPUTTYPE.SHOOT)) {
            shoot();
        }
    }

    @Override
    public void render(Graphics g) {
        int xCenter = x - image.getWidth(null)/2;
        int yCenter = y - image.getHeight(null)/2;
        g.drawImage(image, xCenter,yCenter, null);
    }
}
