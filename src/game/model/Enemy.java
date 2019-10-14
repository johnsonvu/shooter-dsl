package game.model;

import evaluate.Evaluator;
import evaluate.protoypes.ProjectileProto;
import game.view.Game;
import lib.enums.Direction;
import evaluate.protoypes.EnemyProto;
import ui.Main;

import java.awt.*;

import static lib.Util.rotate;
import static lib.enums.Direction.*;
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
        this.health = (health < 0) ? 0 : health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() { return health; }

    public void shoot(Direction dir) {
        if (shootingCounter == shootingDelay) {
            Projectile p = new Projectile(new ProjectileProto(id, damage, health), id, dir, this);
            p.x = dir == RIGHT? x + image.getWidth() : dir == LEFT? x - image.getWidth(): x;
            p.y = dir == DOWN? y + image.getHeight() : dir == UP? y - image.getHeight(): y;
            p.moveSpeed = this.shootingSpeed;
            Main.gameObjects.add(p);
            shootingCounter = 0;
        } else {
            shootingCounter++;
        }
    }

    @Override
    public void move(Direction dir){
        switch (dir) {
            case UP:
                // image rotation
                if(facing == LEFT) {
                    image = rotate(image, 90);
                } else if (facing == RIGHT) {
                    image = rotate(image, -90);
                } else if (facing == DOWN) {
                    image = rotate(image, 180);
                }
                facing = UP;

                if (checkBound(x, y, dir)) {
                    //super.move(dir);/
                    this.y -= moveSpeed/2;
                } else {
                    Main.gameObjects.remove(this);
                }
                break;
            case DOWN:
                // image rotation
                if(facing == LEFT) {
                    image = rotate(image, -90);
                } else if (facing == RIGHT) {
                    image = rotate(image, 90);
                } else if (facing == UP) {
                    image = rotate(image, 180);
                }
                facing = DOWN;

                if (checkBound(x, y, dir)) {
                    //super.move(dir);/
                    this.y += moveSpeed/2;
                } else {
                    Main.gameObjects.remove(this);
                }
                break;
            case LEFT:
                // image rotation
                if(facing == RIGHT) {
                    image = rotate(image, 180);
                } else if (facing == UP) {
                    image = rotate(image, -90);
                } else if (facing == DOWN) {
                    image = rotate(image, 90);
                }
                facing = LEFT;

                x = (x <= 0) ? Game.getInstance().getWidth() : x - moveSpeed;
                break;
            default:
                // image rotation
                if(facing == LEFT) {
                    image = rotate(image, 180);
                } else if (facing == UP) {
                    image = rotate(image, 90);
                } else if (facing == DOWN) {
                    image = rotate(image, -90);
                }
                facing = RIGHT;
                x = (x >= Game.getInstance().getWidth()) ? 0 : x + moveSpeed;
                break;
        }
    }

    @Override
    public void tick() {
        Evaluator eval = Evaluator.getInstance();
        eval.run(this.proto.behaviour, this);
        checkDeath();
    }

    private void checkDeath(){
        if(health <= 0)
            Main.gameObjects.remove(this);
    }

    @Override
    public void render(Graphics g) {
        int xCenter = x - image.getWidth(null)/2;
        int yCenter = y - image.getHeight(null)/2;
        g.drawImage(image, xCenter,yCenter, null);
    }
}
