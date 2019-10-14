package ast;

import visitor.Visitor;

public class Direction extends ASTNode{
    public lib.enums.Direction direction;

    public Direction(String direction) {
        this.direction = lib.enums.Direction.valueOf(direction.toUpperCase());
    }
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
