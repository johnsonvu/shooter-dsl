package game.model;

import ast.FunctionBlock;
import ast.Identifier;
import evaluate.protoypes.GameObjectProto;
import lib.DIRECTION;

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
//    protected SpriteSheet ss;

    public GameObject(GameObjectProto proto, String name) {
        this.x = 1;
        this.y = 1;
        this.id = name;
//        this.ss = ss;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

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

}
