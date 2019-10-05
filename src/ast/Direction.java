package ast;

import lib.DIRECTION;
import visitor.Visitor;

public class Direction extends ASTNode{
    public DIRECTION direction;

    public Direction(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                this.direction = DIRECTION.UP;
                break;
            case "down":
                this.direction = DIRECTION.DOWN;
                break;
            case "left":
                this.direction = DIRECTION.LEFT;
                break;
            case "right":
                this.direction = DIRECTION.RIGHT;
                break;
            default:
                this.direction = DIRECTION.RIGHT;
        }
    }
    
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
