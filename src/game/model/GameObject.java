package game.model;

import ast.Identifier;
import game.controller.Handler;
import game.view.Game;
import lib.DIRECTION;

import java.awt.*;
import java.awt.image.BufferedImage;

import evaluate.protoypes.GameObjectProto;

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
    protected BufferedImage image;
//    protected SpriteSheet ss;

    public static final int MOVE_CONSTANT = 5;

    public GameObject(GameObjectProto proto, String name) {
        this.x = 1;
        this.y = 1;
        this.id = name;
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

    public boolean checkBound(int x, int y, DIRECTION dir) {
        switch (dir) {
            case UP:
                return inBound(x, y - MOVE_CONSTANT);
            case DOWN:
                return inBound(x, y + MOVE_CONSTANT);
            case LEFT:
                return inBound(x - MOVE_CONSTANT, y);
            default:
                return inBound(x + MOVE_CONSTANT, y);
        }

    }

    public boolean inBound(int x, int y) {
        Game game = Game.getInstance();
        return 0 <= x && x <= game.getWidth() - image.getWidth() && 0 <= y && y <= game.getHeight() - image.getHeight();
    }
}
