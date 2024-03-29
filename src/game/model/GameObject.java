package game.model;

import ast.Identifier;
import game.view.Game;
import lib.enums.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;

import evaluate.protoypes.GameObjectProto;

import static lib.Util.randomInt;
import static lib.Util.rotate;
import static lib.enums.Direction.*;
import static lib.enums.Direction.DOWN;

public abstract class GameObject {
    public int number;
    public int damage;
    public int health;
    public Identifier behaviour;
    public GameObjectProto proto;

    protected int x;
    protected int y;
    protected float velX = 0;
    protected float velY = 0;
    protected String id;
    public BufferedImage image;
    protected Direction facing;
//    protected SpriteSheet ss;

    protected int moveSpeed;

    public GameObject(GameObjectProto proto, String name) {
        this.proto = proto;
        // randomly spawn game objects
        this.x = randomInt(100, Game.getInstance().getWidth() - 100);
        this.y = randomInt(100, Game.getInstance().getHeight() - 100);
        this.id = name;
        this.moveSpeed = 4;
        this.facing = UP;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public Rectangle getBounds() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void move(Direction dir) {
        Game game = Game.getInstance();
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
                y -= moveSpeed;
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
                y += moveSpeed;
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
                x = (x < 0) ? game.getWidth() : x - moveSpeed;
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
                x = (x > game.getWidth()) ? 0 : x + moveSpeed;
                break;
        }
    }

    public boolean checkBound(int x, int y, Direction dir) {
        switch (dir) {
            case UP:
                return inBound(x, y - moveSpeed);
            case DOWN:
                return inBound(x,y + moveSpeed);
            case LEFT:
                return inBound(x - moveSpeed, y);
            default:
                return inBound(x + moveSpeed, y);
        }
    }

    public boolean inBound(int x, int y) {
        Game game = Game.getInstance();
        return /* 0 <= x && x <= game.getWidth() - image.getWidth() && */ 0 <= y && y <= game.getHeight() - image.getHeight();
    }

    public void apply(GameObjectProto gop) {
        this.damage = gop.damage;
        this.health = gop.health;
        this.behaviour = gop.behaviour;
    }

    public GameObjectProto makeProto(String name) {
        return new GameObjectProto(name, 1, 1);
    }
}
