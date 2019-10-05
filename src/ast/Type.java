package ast;

import lib.TYPE;
import visitor.Visitor;

public class Type extends ASTNode{
    public TYPE type;

    public Type(String type) {
        switch(type.toLowerCase()) {
            case "player":
                this.type = TYPE.PLAYER;
                break;
            case "enemy":
                this.type = TYPE.ENEMY;
                break;
            case "projectile":
                this.type = TYPE.PROJECTILE;
                break;
            case "item":
                this.type = TYPE.ITEM;
                break;
            default:
                this.type = TYPE.PLAYER;
                break;
        }
    }
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
