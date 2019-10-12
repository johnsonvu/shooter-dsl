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



    public void move(DIRECTION direction, int steps){

    }

    public void shoot(DIRECTION direction){

    }

}
