package game.model;

import lib.DIRECTION;

public abstract class GameObject {
    public String name;
    public int number;
    public int damage;
    public int health;



    public void move(DIRECTION direction, int steps){

    }

    public void shootDirection(DIRECTION direction){

    }

}
