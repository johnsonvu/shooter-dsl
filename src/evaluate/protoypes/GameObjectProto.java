package evaluate.protoypes;

import ast.Identifier;

public abstract class GameObjectProto {
    public int damage;
    public int health;
    public Identifier behaviour;

    GameObjectProto(int damage, int health)
    {
        this.damage = damage;
        this.health = health;
    }
}
