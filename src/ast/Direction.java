package ast;

import visitor.Visitor;

public class Direction extends ASTNode{
    public lib.enums.Direction direction;

    public Direction(String direction) {
        try {
            this.direction = lib.enums.Direction.valueOf(direction.toUpperCase());
        }catch (Exception e){
            System.err.println("Expected \"down\", \"up\", \"left\", or \"right\" but found \"" + direction + "\"");
            System.exit(0);
        }

    }
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
