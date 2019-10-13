package ast;

import lib.DIRECTION;
import visitor.Visitor;

public class Direction extends ASTNode{
    public DIRECTION direction;

    public Direction(String direction) {
        this.direction = DIRECTION.valueOf(direction);
    }
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
