package game.model;

import ast.FunctionBlock;
import ast.Identifier;
import lib.DIRECTION;

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
//    protected SpriteSheet ss;

    public GameObject(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
//        this.ss = ss;
    }

    public void move(DIRECTION direction, int steps){

    }

    public void shoot(DIRECTION direction){

    }

}
