package evaluate.protoypes;

import ast.Identifier;

public class GameObjectProto {
    public String name;
    public int damage;
    public int health;
    public Identifier behaviour;

    public GameObjectProto(String name, int damage, int health)
    {
        this.name = name;
        this.damage = damage;
        this.health = health;
    }
}
