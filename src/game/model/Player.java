package game.model;

import evaluate.protoypes.ProjectileProto;
import game.controller.Audio;
import game.view.Game;
import lib.enums.Direction;
import lib.enums.KeyInputType;
import evaluate.protoypes.PlayerProto;
import ui.Main;

import java.awt.*;
import java.util.HashMap;

import static lib.enums.Direction.*;
import static lib.Util.randomInt;
import static lib.Util.rotate;

public class Player extends GameObject {
    private Audio audio;
    private int shootingDelay = 4;
    private int shootingCounter = 0;
    private int shootingSpeed = 7;
    private Direction facing;

    public Player(PlayerProto proto, String name) {
        super(proto, name);
        this.damage = proto.damage;
        this.health = proto.health;
        this.moveSpeed = 6;
        this.facing = UP;

        // spawn at middle of the bottom of the game
        super.x = Game.getInstance().getWidth()/2;
        super.y = Game.getInstance().getHeight() - 100;
        image = Game.sprite.loadImage(this);

        audio = new Audio("media/Audio/Shoot/3.wav");
        Game.getInstance().getHandler().objectStates.put(this, new HashMap<KeyInputType, Boolean>());
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
            Projectile p = new Projectile(new ProjectileProto(id, damage, health), id, facing, this);
            // set projectile to shoot from player
            p.x = facing == RIGHT? x + image.getWidth() : facing == LEFT? x - image.getWidth(): x;
            p.y = facing == DOWN? y + image.getHeight() : facing == UP? y - image.getHeight(): y;
            p.moveSpeed = this.shootingSpeed;
            Main.gameObjects.add(p);
            audio.play();
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
        HashMap<KeyInputType, Boolean> map = Game.getInstance().getHandler().objectStates.get(this);
        if (map.containsKey(KeyInputType.UP) && map.get(KeyInputType.UP)) {
            // image rotation
            if(facing == LEFT) {
                image = rotate(image, 90);
            } else if (facing == RIGHT) {
                image = rotate(image, -90);
            } else if (facing == DOWN) {
                image = rotate(image, 180);
            }

            // boundary condition for top
            if (checkBound(x, y, UP)) {
                move(UP);
                facing = UP;
            }
        }

        if (map.containsKey(KeyInputType.DOWN) && map.get(KeyInputType.DOWN)) {
            // image rotation
            if(facing == LEFT) {
                image = rotate(image, -90);
            } else if (facing == RIGHT) {
                image = rotate(image, 90);
            } else if (facing == UP) {
                image = rotate(image, 180);
            }

            // move down
            if (checkBound(x, y, DOWN)) {
                move(DOWN);
                facing = DOWN;
            }
        }

        if (map.containsKey(KeyInputType.LEFT) && map.get(KeyInputType.LEFT)) {
            // image rotation
            if(facing == RIGHT) {
                image = rotate(image, 180);
            } else if (facing == UP) {
                image = rotate(image, -90);
            } else if (facing == DOWN) {
                image = rotate(image, 90);
            }

            // move left
            move(LEFT);
            facing = LEFT;
        }

        if (map.containsKey(KeyInputType.RIGHT) && map.get(KeyInputType.RIGHT)) {
            // image rotation
            if(facing == LEFT) {
                image = rotate(image, 180);
            } else if (facing == UP) {
                image = rotate(image, 90);
            } else if (facing == DOWN) {
                image = rotate(image, -90);
            }

            // move right
            move(RIGHT);
            facing = RIGHT;
        }

        if (map.containsKey(KeyInputType.SHOOT) && map.get(KeyInputType.SHOOT)) {
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
