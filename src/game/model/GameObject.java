package game.model;

import ast.Identifier;
import lib.DIRECTION;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    public String name;
    public int number;
    public int damage;
    public int health;
    public Identifier behaviour;

    protected int x;
    protected int y;
    protected float velX = 0;
    protected float velY = 0;
    protected String id;
    protected BufferedImage image;
//    protected SpriteSheet ss;

    public static final int MOVE_CONSTANT = 5;

    public GameObject(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
//        this.ss = ss;
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

    public void move(DIRECTION dir) {
        switch (dir) {
            case UP:
                y -= MOVE_CONSTANT;
                break;
            case DOWN:
                y += MOVE_CONSTANT;
                break;
            case LEFT:
                x -= MOVE_CONSTANT;
                break;
            default:
                x += MOVE_CONSTANT;
        }
    }

}
