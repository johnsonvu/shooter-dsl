package evaluate.protoypes;

import ast.Identifier;

public abstract class GameObjectProto {
    public String name;
    public int damage;
    public int health;
    public Identifier behaviour;

    GameObjectProto(String name, int health, int damage)
    {
        this.name = name;
        this.damage = damage;
        this.health = health;
    }
}
