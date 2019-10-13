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

    protected int moveSpeed;

    public GameObject(GameObjectProto proto, String name) {
        this.x = 1;
        this.y = 1;
        this.id = name;
        this.moveSpeed = 4;
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
        Game game = Game.getInstance();
        switch (dir) {
            case UP:
                y -= moveSpeed;
                break;
            case DOWN:
                y += moveSpeed;
                break;
            case LEFT:
                x = (x + image.getWidth()/2 < 0) ? game.getWidth() : x - moveSpeed;
                break;
            default:
                x = (x + image.getWidth()/2 > game.getWidth()) ? 0 : x + moveSpeed;
                break;
        }
    }

    public boolean checkBound(int x, int y, DIRECTION dir) {
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
        return 0 <= x && x <= game.getWidth() - image.getWidth() && 0 <= y && y <= game.getHeight() - (image.getHeight()*2);
    }
}
