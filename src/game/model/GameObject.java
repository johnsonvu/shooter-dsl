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

    public void move(DIRECTION direction, int steps){

    }

    public void shoot(DIRECTION direction){

    }

}
